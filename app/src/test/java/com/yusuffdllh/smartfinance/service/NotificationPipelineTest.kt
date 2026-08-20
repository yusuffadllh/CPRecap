package com.yusuffdllh.smartfinance.service

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * End-to-end style unit tests for the LOCAL transaction pipeline that powers
 * BOTH the Notification-bar reader and the Gmail sync feature.
 *
 * The on-device service ([NotificationReaderService]) and Gmail path both end
 * up calling [RuleEngine.predict] (via AiService.predictLocally / the AI
 * fallback which reuses the rule result). These tests exercise that shared core
 * against realistic bank / e-wallet / email content, WITHOUT requiring a real
 * bank app or a live Gmail account on the device.
 *
 * Key behaviours verified:
 *  - Genuine bank transactions across multiple trusted packages are detected.
 *  - Income vs expense classification.
 *  - Amount extraction across formats.
 *  - Confidence thresholds that drive the service's AUTO-SAVE (>= 0.75) vs
 *    DRAFT (< 0.75) decision.
 *  - Promotions and failed transactions are filtered out (as the service does
 *    before ever touching the DB), even for trusted apps.
 */
class NotificationPipelineTest {

    private lateinit var engine: RuleEngine

    // Trusted package ids exactly as whitelisted in the service.
    private val dana = "com.dana"
    private val bcaMobile = "id.co.bca.mobile"
    private val brimo = "com.bri.brimo"
    private val livin = "com.bankmandiri.livin"
    private val gopay = "com.gopay.app"
    private val gmail = "com.google.android.gm"

    /** Mirrors NotificationReaderService: confidence >= 0.75 => auto-save. */
    private fun isAutoSave(confidence: Float) = confidence >= 0.75f

    @Before
    fun setUp() {
        engine = RuleEngine()
    }

    // ---------------- Genuine bank transactions (multi-app) ----------------

    @Test
    fun bcaMobile_expense_isDetected() {
        val text = "Transfer Berhasil Rp 50.000 ke MIXUE."
        val r = engine.predict(text, bcaMobile)
        assertTrue(r.isTransaction)
        assertEquals(50_000L, r.amount)
        assertEquals("EXPENSE", r.type)
        // MIXUE is in the merchant dictionary -> high confidence + category.
        assertEquals("Makanan", r.category)
        assertEquals(1.0f, r.confidence, 0.0001f)
    }

    @Test
    fun brimo_incomeSalary_isDetected() {
        val text = "Dana masuk Gaji Rp 5.000.000 telah diterima di rekening Anda."
        val r = engine.predict(text, brimo)
        assertTrue(r.isTransaction)
        assertEquals(5_000_000L, r.amount)
        assertEquals("INCOME", r.type)
    }

    @Test
    fun livin_qrisPayment_isExpense() {
        val text = "Pembayaran QRIS Rp 27.500 berhasil di Alfamart."
        val r = engine.predict(text, livin)
        assertTrue(r.isTransaction)
        assertEquals(27_500L, r.amount)
        assertEquals("EXPENSE", r.type)
        assertEquals("Belanja", r.category) // alfamart -> Belanja
    }

    @Test
    fun gopay_payment_isDetected() {
        val text = "Pembayaran Rp 18.000 ke GoFood berhasil."
        val r = engine.predict(text, gopay)
        assertTrue(r.isTransaction)
        assertEquals(18_000L, r.amount)
        assertEquals("Makanan", r.category) // gofood -> Makanan
    }

    // ---------------- Confidence -> AUTO-SAVE vs DRAFT ----------------

    @Test
    fun knownMerchant_confidenceTriggersAutoSave() {
        // Dictionary merchant => confidence 1.0 => auto-save path.
        val r = engine.predict("Transfer Berhasil Rp 40.000 ke Starbucks", bcaMobile)
        assertTrue(r.isTransaction)
        assertTrue(isAutoSave(r.confidence))
    }

    @Test
    fun unknownMerchantTrustedApp_confidenceIsAtLeastDraftFloor() {
        // No dictionary/regex match, but trusted bank => confidence boosted to 0.75.
        val r = engine.predict("Transaksi Berhasil Rp 12.345", brimo)
        assertTrue(r.isTransaction)
        // Exactly at the auto-save boundary for trusted apps.
        assertTrue(isAutoSave(r.confidence))
        assertEquals(0.75f, r.confidence, 0.0001f)
    }

    // ---------------- Promotions / failures are filtered ----------------

    @Test
    fun trustedApp_promotion_isRejected() {
        val text = "Promo cashback Rp 100.000 pakai kode, buruan klaim sekarang!"
        assertFalse(engine.isRealTransaction(text, dana))
        val r = engine.predict(text, dana)
        assertFalse(r.isTransaction)
        assertEquals(0f, r.confidence, 0.0001f)
    }

    @Test
    fun trustedApp_failedTransaction_isRejected() {
        val text = "Transaksi GAGAL. Pembayaran Rp 75.000 ditolak, saldo tidak cukup."
        assertFalse(engine.isRealTransaction(text, livin))
        assertFalse(engine.predict(text, livin).isTransaction)
    }

    // ---------------- Gmail path (same core) ----------------

    @Test
    fun gmail_receiptEmail_isDetectedAsTransaction() {
        // Simulates a resolved e-receipt body handled after gmailHelper fetch.
        val body = "Pembayaran berhasil. Total Rp 150.000 telah dibayar ke Tokopedia. Terima kasih."
        val r = engine.predict(body, gmail)
        assertTrue(r.isTransaction)
        assertEquals(150_000L, r.amount)
        assertEquals("Belanja", r.category) // tokopedia -> Belanja
    }

    @Test
    fun gmail_promoNewsletter_isIgnored() {
        val body = "Flash sale spesial! Diskon hingga 90%, gratis ongkir. Belanja sekarang di aplikasi."
        assertFalse(engine.isRealTransaction(body, gmail))
        assertFalse(engine.predict(body, gmail).isTransaction)
    }

    // ---------------- Amount extraction robustness ----------------

    @Test
    fun amount_withCents_isTruncated() {
        val r = engine.predict("Pembayaran Rp 99.900,00 berhasil ke Indomaret", bcaMobile)
        assertEquals(99_900L, r.amount)
    }

    @Test
    fun amount_idrPrefix_isParsed() {
        val r = engine.predict("Debit IDR 250.000 berhasil untuk Netflix", livin)
        assertEquals(250_000L, r.amount)
        assertEquals("Hiburan", r.category) // netflix -> Hiburan
    }
}
