package com.yusuffdllh.smartfinance.service

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [RuleEngine], the local (offline) transaction parser.
 *
 * These validate the notification pipeline logic WITHOUT needing a real bank
 * app on a device: genuine-transaction detection, promotion filtering, failed
 * transaction filtering, type detection and amount extraction.
 *
 * Text samples are neutral/generic (no brand impersonation); only the parsing
 * behaviour is under test.
 */
class RuleEngineTest {

    private lateinit var engine: RuleEngine

    // A representative trusted e-wallet/bank package id used by the service.
    private val trustedPkg = "com.dana"
    private val untrustedPkg = "com.example.randomapp"

    @Before
    fun setUp() {
        engine = RuleEngine()
    }

    // ---------- Genuine transactions ----------

    @Test
    fun trustedApp_withSuccessMarker_isTransaction() {
        val text = "Pembayaran QRIS Rp 25.000 Berhasil."
        assertTrue(engine.isRealTransaction(text, trustedPkg))

        val result = engine.predict(text, trustedPkg)
        assertTrue(result.isTransaction)
        assertEquals(25_000L, result.amount)
        assertEquals("EXPENSE", result.type)
    }

    @Test
    fun trustedApp_incomeMessage_detectedAsIncome() {
        val text = "Dana masuk Rp 1.500.000 telah diterima di saldo Anda."
        val result = engine.predict(text, trustedPkg)
        assertTrue(result.isTransaction)
        assertEquals(1_500_000L, result.amount)
        assertEquals("INCOME", result.type)
    }

    // ---------- Promotion filtering ----------

    @Test
    fun promotion_isDetected() {
        assertTrue(engine.isPromotion("Promo hemat pakai voucher ini!"))
        assertTrue(engine.isPromotion("Dapatkan cashback dan diskon spesial"))
        assertTrue(engine.isPromotion("Menangkan hadiah, klaim sekarang!"))
    }

    @Test
    fun promotion_evenWithAmount_isNotTransaction() {
        // Promos from banks/e-wallets often contain "Rp" amounts too.
        val text = "Promo cashback Rp 500 pakai voucher, buruan klaim!"
        assertFalse(engine.isRealTransaction(text, trustedPkg))

        val result = engine.predict(text, trustedPkg)
        assertFalse(result.isTransaction)
        assertEquals(0f, result.confidence, 0.0001f)
    }

    // ---------- Failure filtering ----------

    @Test
    fun failedTransaction_isDetected() {
        assertTrue(engine.isFailure("Transaksi GAGAL. Saldo tidak mencukupi."))
        assertTrue(engine.isFailure("Pembayaran ditolak"))
    }

    @Test
    fun failedTransaction_isNotTransaction() {
        val text = "Transaksi GAGAL. Saldo tidak mencukupi untuk bayar Rp 10.000."
        assertFalse(engine.isRealTransaction(text, trustedPkg))
    }

    // ---------- Non-transaction / noise ----------

    @Test
    fun untrustedApp_withoutIntentKeyword_isNotTransaction() {
        // A generic chat app mentioning Rp but no transaction intent.
        val text = "Ketemuan yuk, bawa Rp 20.000 aja"
        // "yuk" is a promo keyword -> blocked; still must not be a transaction.
        assertFalse(engine.isRealTransaction(text, untrustedPkg))
    }

    @Test
    fun emptyOrTooShort_isNotTransaction() {
        assertFalse(engine.isRealTransaction("", trustedPkg))
        assertFalse(engine.isRealTransaction("Rp", trustedPkg))
    }

    // ---------- Amount extraction ----------

    @Test
    fun amountExtraction_handlesThousandSeparators() {
        assertEquals(50_000L, engine.predict("Transfer Berhasil Rp 50.000", trustedPkg).amount)
    }

    @Test
    fun amountExtraction_handlesNoSeparator() {
        assertEquals(75000L, engine.predict("Kirim Rp75000 Berhasil", trustedPkg).amount)
    }

    @Test
    fun amountExtraction_handlesIdrPrefix() {
        assertEquals(100_000L, engine.predict("Pembayaran IDR 100.000 sukses", trustedPkg).amount)
    }

    @Test
    fun amountExtraction_handlesDecimalCommaCents() {
        // "Rp 50.000,00" -> cents stripped -> 50000
        assertEquals(50_000L, engine.predict("Pembayaran Rp 50.000,00 berhasil", trustedPkg).amount)
    }

    // ---------- Type detection ----------

    @Test
    fun detectType_incomeKeywordsWin() {
        assertEquals("INCOME", engine.detectType("Gaji masuk dan diterima"))
    }

    @Test
    fun detectType_expenseKeywordsWin() {
        assertEquals("EXPENSE", engine.detectType("Bayar tagihan listrik"))
    }
}
