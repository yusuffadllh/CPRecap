package com.yusuffdllh.smartfinance.service

import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuleEngine @Inject constructor() {

    private val merchantDictionary = mapOf(
        "mixue" to "Makanan",
        "kfc" to "Makanan",
        "starbucks" to "Makanan",
        "mcdonald" to "Makanan",
        "grabfood" to "Makanan",
        "gofood" to "Makanan",
        "shopeefood" to "Makanan",
        "solaria" to "Makanan",
        "pertamina" to "Transportasi",
        "shell" to "Transportasi",
        "grab" to "Transportasi",
        "gojek" to "Transportasi",
        "bluebird" to "Transportasi",
        "tokopedia" to "Belanja",
        "shopee" to "Belanja",
        "lazada" to "Belanja",
        "indomaret" to "Belanja",
        "alfamart" to "Belanja",
        "steam" to "Hiburan",
        "spotify" to "Hiburan",
        "netflix" to "Hiburan",
        "pln" to "Tagihan",
        "pdam" to "Tagihan",
        "telkom" to "Tagihan",
        "bpjs" to "Tagihan",
        "halodoc" to "Kesehatan"
    )

    private val incomeKeywords = listOf(
        "dana masuk", "penerimaan", "kredit", "refund", "salary", "gaji", 
        "terima dana", "terima transfer", "incoming"
    )

    private val expenseKeywords = listOf(
        "kirim", "keluar", "bayar", "purchase", "debit", "pembayaran", "qris", 
        "expense", "payment", "tagihan", "transfer ke", "berhasil transfer"
    )

    private val transactionKeywords = incomeKeywords + expenseKeywords + listOf("berhasil", "sukses", "idr", "rp", "transfer", "kirim")

    private val promotionKeywords = listOf(
        "promo", "diskon", "voucher", "menangkan", "hadiah", "yuk", "cek", 
        "terbatas", "exclusive", "sale", "hemat", "claim", "bonus", "peluang",
        "makan mana", "buruan", "special offer", "mulai dari", "cuma rp", 
        "kesempatan", "untung", "investasi", "kado", "ajak", "pake", "kode", "nikmati",
        "discount", "kupon", "cashback", "cash back", "eksklusif", "klaim", "gratis",
        "free", "penawaran", "cuma", "hanya rp", "pakai kode", "kode promo",
        "kode voucher", "flash sale", "big sale", "harbolnas", "serba", "murah",
        "dapatkan", "poin", "point", "reward", "undian", "giveaway", "spesial",
        "special", "jangan lewatkan", "berlaku hingga", "berlaku sampai", "paylater",
        "pinjaman", "limit kartu", "aktifkan sekarang", "daftar sekarang", "download",
        "unduh", "install", "upgrade", "isi saldo", "kumpulkan", "tukar poin",
        "member", "membership", "gratis ongkir", "cicilan"
    )

    private val failureKeywords = listOf(
        "gagal", "tidak berhasil", "ditolak", "dibatalkan", "tidak cukup", 
        "gangguan", "error", "failed", "cancelled", "rejected", "insufficient",
        "tidak memadai", "unsuccessful", "timeout", "waktu habis", "melebihi limit",
        "terlampaui", "pin salah", "autentikasi gagal", "declined", "batal", "limit"
    )

    private val trustedPackages = listOf(
        "com.dana", "com.gopay.app", "com.gojek.app", "com.shopee.id",
        "id.ovo.android", "com.telkomsel.linkaja", "com.doku.wallet",
        "com.bt.bclient", "com.jago.jago", "com.neobank.indonesia", 
        "id.co.anypay.blu", "id.co.bca.mobile", "id.co.bca.blue",
        "com.bri.brimo", "com.bankmandiri.livin", "com.bni.mbanking",
        "id.co.bni.newmobile"
    )

    /**
     * Real-transaction success markers. A genuine transaction receipt almost
     * always confirms that money actually moved. Promotions never contain these.
     */
    private val successMarkers = listOf(
        "berhasil", "sukses", "success", "telah", "diterima",
        "terbayar", "terkirim", "dibayar", "pembayaran", "transaksi",
        "transfer", "debet", "debit", "kredit", "penarikan", "setoran",
        "pembelian", "qris", "trx", "ref", "saldo anda",
        "saldo tersisa", "sisa saldo", "e-receipt", "struk"
    )

    /**
     * Explicitly detects promotional / marketing / advertising content so the
     * caller can drop it before any transaction is ever created.
     */
    fun isPromotion(text: String): Boolean {
        val lowText = text.lowercase()
        return promotionKeywords.any { lowText.contains(it) }
    }

    /**
     * Detects failed / rejected / cancelled transactions.
     */
    fun isFailure(text: String): Boolean {
        val lowText = text.lowercase()
        return failureKeywords.any { lowText.contains(it) }
    }

    /**
     * Identifies if a text represents a valid, completed transaction.
     */
    fun isRealTransaction(text: String, packageName: String? = null): Boolean {
        if (text.length < 3) return false
        val lowText = text.lowercase()

        // 1. Hard block: promotions and failures are NEVER transactions,
        //    regardless of which app they came from (banks/e-wallets send
        //    promos with "Rp" amounts too).
        if (isFailure(lowText)) return false
        if (isPromotion(lowText)) return false

        // 2. An amount is mandatory for every real transaction.
        val amount = extractAmount(text, packageName)
        if (amount <= 0) return false

        // 3. Trusted apps (banks / e-wallets): accept only if the message
        //    also confirms an actual money movement (success marker), so that
        //    generic marketing pushes without such markers are ignored.
        if (packageName != null && trustedPackages.contains(packageName)) {
            return successMarkers.any { lowText.contains(it) }
        }

        // 4. Generic apps: require an explicit transaction intent keyword.
        val hasIntent = transactionKeywords.any { lowText.contains(it) }
        return hasIntent
    }

    /**
     * Determines transaction type with high priority for income signals.
     */
    fun detectType(text: String): String {
        val lowText = text.lowercase()
        val hasIncome = incomeKeywords.any { lowText.contains(it) }
        val hasExpense = expenseKeywords.any { lowText.contains(it) }
        
        // Cek kata "terima kasih" agar tidak salah deteksi income
        val cleanIncome = hasIncome && !lowText.contains("terima kasih")
        
        return when {
            // Prioritaskan Expense bila bayar pakai QRIS
            lowText.contains("qris") || lowText.contains("bayar pakai") -> "EXPENSE"
            hasExpense && !cleanIncome -> "EXPENSE"
            cleanIncome && !hasExpense -> "INCOME"
            lowText.contains("transfer ke") -> "EXPENSE"
            lowText.contains("dari") && !lowText.contains("terima kasih") -> "INCOME"
            lowText.contains("ke") -> "EXPENSE"
            lowText.contains("untuk") -> "EXPENSE"
            else -> "EXPENSE" // Default fallback to Expense
        }
    }

    fun predict(text: String, packageName: String? = null): PredictionResult {
        val lowText = text.lowercase()
        val isTransaction = isRealTransaction(text, packageName)
        val amount = extractAmount(text, packageName)
        val type = detectType(text)
        
        var detectedMerchant = "Transaksi Baru"
        var detectedCategory = "Umum"
        var confidence = 0.4f

        if (isTransaction) {
            // 1. Dictionary Match
            var found = false
            for ((keyword, cat) in merchantDictionary) {
                if (lowText.contains(keyword)) {
                    val cleanName = keyword.replaceFirstChar { it.uppercase() }
                    detectedMerchant = if (type == "INCOME") "Terima dari $cleanName" else "Bayar $cleanName"
                    detectedCategory = cat
                    confidence = 1.0f 
                    found = true
                    break
                }
            }

            // 2. Regex Fallback
            if (!found) {
                val extracted = extractMerchant(text)
                if (extracted != null) {
                    detectedMerchant = if (type == "INCOME") "Terima dari $extracted" else "Transfer ke $extracted"
                    confidence = 0.85f 
                }
            }
            
            // 3. Status boost for known bank apps
            if (detectedMerchant == "Transaksi Baru") {
                detectedMerchant = if (type == "INCOME") "Transfer Masuk" else "Transfer Keluar"
                if (packageName != null && trustedPackages.contains(packageName)) {
                    confidence = 0.75f 
                }
            }
        }

        return PredictionResult(
            isTransaction = isTransaction,
            merchant = detectedMerchant,
            category = detectedCategory,
            amount = amount,
            type = type,
            confidence = if (isTransaction) confidence else 0f
        )
    }

    fun predictCategory(text: String): String? {
        val res = predict(text)
        return if (res.confidence >= 0.7f) res.category else null
    }

    private fun extractMerchant(text: String): String? {
        val patterns = listOf(
            Pattern.compile("(?i)(?:ke|at|di|untuk|transfer|pembayaran|penerima)\\s+([A-Z0-9.\\s\\-]{3,35})"),
            Pattern.compile("(?i)(?:dari|pengirim)\\s+([A-Z0-9.\\s\\-]{3,35})"),
            Pattern.compile("(?i)(?:merchant|toko|outlet)\\s+([A-Z0-9.\\s\\-]{3,35})")
        )

        for (p in patterns) {
            val m = p.matcher(text)
            if (m.find()) {
                var candidate = m.group(1)?.trim() ?: ""
                if (candidate.isNotEmpty()) {
                    candidate = candidate.split(
                        "Rp", "IDR", "berhasil", "sukses", "tanggal", "jam", "\n", "\r", 
                        "trx", "ref", "m-bca", "mbca", "va", "virtual account", "sumber"
                    )[0].trim()
                    
                    if (candidate.length >= 3) return candidate
                }
            }
        }
        return null
    }

    private fun extractAmount(text: String, packageName: String? = null): Long {
        val amountPattern = Pattern.compile("(?i)(?:Rp|IDR|Rp\\.|IDR\\.)\\s?([\\d.,]+)")
        val m = amountPattern.matcher(text)
        
        var raw = if (m.find()) m.group(1) ?: "" else ""

        if (raw.isEmpty() && packageName != null && trustedPackages.contains(packageName)) {
            val lenient = Pattern.compile("\\b([\\d]{4,12})\\b")
            val lm = lenient.matcher(text)
            if (lm.find()) raw = lm.group(1) ?: ""
        }

        if (raw.isNotEmpty()) {
            if (raw.contains(",") && raw.substringAfterLast(",").length <= 2) {
                raw = raw.substringBeforeLast(",")
            }
            val cleaned = raw.replace(".", "").replace(",", "").replace(" ", "")
            return cleaned.toLongOrNull() ?: 0L
        }
        return 0L
    }
}

data class PredictionResult(
    val isTransaction: Boolean = false,
    val merchant: String,
    val category: String,
    val amount: Long,
    val type: String = "EXPENSE",
    val confidence: Float
)
