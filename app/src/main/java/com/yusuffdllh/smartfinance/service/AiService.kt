package com.yusuffdllh.smartfinance.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.yusuffdllh.smartfinance.data.local.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiService @Inject constructor(
    private val userPreferences: UserPreferences,
    private val ruleEngine: RuleEngine,
    private val gson: Gson
) {
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private companion object {
        const val TAG = "AiService"
    }
    /**
     * Hybrid parser: Uses local rules first, fallback to Gemini AI for complexity.
     * Strictly restricted to transaction data extraction for high accuracy.
     */
    suspend fun predictTransaction(text: String, packageName: String? = null): PredictionResult {
        // 0. Hard guard: never treat promotions or failed transactions as income/expense.
        //    The AI must NOT be able to override this decision.
        if (ruleEngine.isPromotion(text) || ruleEngine.isFailure(text)) {
            return PredictionResult(
                isTransaction = false,
                merchant = "",
                category = "Lainnya",
                amount = 0L,
                type = "EXPENSE",
                confidence = 0f
            )
        }

        // 1. Initial Rule Engine check (Localized & Instant)
        val ruleResult = ruleEngine.predict(text, packageName)
        if (ruleResult.confidence >= 1.0f || !ruleResult.isTransaction) {
            return ruleResult
        }

        // 2. AI Fallback (Gemini-compatible HTTP) for complex/unknown merchants.
        //    Uses a user-configurable Base URL, API Key and Model.
        return try {
            val apiKey = userPreferences.geminiApiKey.first()
            if (apiKey.isEmpty()) return ruleResult

            val baseUrl = userPreferences.aiBaseUrl.first()
                .ifBlank { UserPreferences.DEFAULT_AI_BASE_URL }
                .trimEnd('/')
            val model = userPreferences.aiModel.first()
                .ifBlank { UserPreferences.DEFAULT_AI_MODEL }

            val prompt = """
                You are a highly accurate Indonesian financial secretary. 
                Summarize this notification text into a natural human-friendly title and classify it into a category.
                
                Strict Naming Rules:
                - Outbound (Expenses): "Bayar [Merchant]", "Beli [Merchant]", or "Transfer ke [Name]"
                - Inbound (Income): "Terima dari [Name]" or "Gaji dari [Name]"
                - General: "Makan di [Merchant]" or "Tagihan [Merchant]"
                
                CRITICAL: IGNORE app names (BCA, GoPay, Mandiri, Gmail) and status words (Berhasil, Sukses). 
                ONLY extract the real destination or source of the money.
                
                Text: "$text"
                
                Classification Guide:
                - Makanan: Restaurant, Cafe, Food, Grocery.
                - Transportasi: Fuel, Parking, Ride-hailing, Tolls.
                - Belanja: Shopping, Fashion, E-commerce (Tokopedia, Shopee).
                - Tagihan: Electricity, Water, Internet, Subscriptions.
                - Hiburan: Games, Movies, Streaming.
                - Kesehatan: Doctor, Hospital, Pharmacy.
                - Pendidikan: Schools, Courses.
                - Gaji: Salary, Main income.
                - Tabungan: Personal savings.
                - Investasi: Stocks, Crypto, Mutual funds.
                - Transfer Masuk: Generic incoming money.
                - Transfer Keluar: Generic outgoing money.
                - Lainnya: If no other category fits.
                
                Output JSON ONLY:
                {"isTransaction": true, "merchant": "Formatted Human Title", "category": "Category Name from Guide", "confidence": 0.95}
            """.trimIndent()
            
            val generatedText = callAi(baseUrl, model, apiKey, prompt) ?: return ruleResult
            var rawJson = generatedText.trim()
            if (rawJson.contains("{")) {
                rawJson = rawJson.substring(rawJson.indexOf("{"), rawJson.lastIndexOf("}") + 1)
            }

            val aiResult = gson.fromJson(rawJson, AiPredictionResponse::class.java)
            val mappedCategory = mapToAppCategory(aiResult.category)
            val finalMerchant = if (aiResult.merchant.isNotBlank() && aiResult.merchant.lowercase() != "unknown") aiResult.merchant else ruleResult.merchant

            PredictionResult(
                // The rule engine already validated this is a real transaction with a
                // positive amount; the AI only enriches merchant/category. If the AI
                // still says it is not a transaction, we honor that as well.
                isTransaction = aiResult.isTransaction,
                merchant = finalMerchant,
                category = mappedCategory,
                amount = ruleResult.amount,
                type = ruleResult.type,
                confidence = aiResult.confidence
            )
        } catch (e: Exception) {
            ruleResult
        }
    }

    /**
     * Calls the configured AI endpoint and returns the generated text, or null
     * on any failure (caller falls back to the local rule engine result).
     *
     * Auto-detects the API dialect:
     * - OpenAI-compatible (default for keys starting with "sk-" or base URLs
     *   containing "/v1" but not "/v1beta"): POST {baseUrl}/chat/completions
     * - Gemini: POST {baseUrl}/v1beta/models/{model}:generateContent?key={apiKey}
     */
    private suspend fun callAi(
        baseUrl: String,
        model: String,
        apiKey: String,
        prompt: String
    ): String? {
        val useOpenAi = apiKey.startsWith("sk-") ||
            (baseUrl.contains("/v1") && !baseUrl.contains("/v1beta"))
        Log.d(TAG, "callAi dialect=${if (useOpenAi) "OpenAI" else "Gemini"} baseUrl=$baseUrl model=$model")
        val result = if (useOpenAi) {
            callOpenAiCompatible(baseUrl, model, apiKey, prompt)
        } else {
            callGeminiRest(baseUrl, model, apiKey, prompt)
        }
        Log.d(TAG, "callAi result=${if (result == null) "NULL (fallback to rules)" else "OK len=${result.length}"}")
        return result
    }

    /**
     * OpenAI-compatible Chat Completions:
     * POST {baseUrl}/chat/completions  (Authorization: Bearer {apiKey})
     * Body: {"model":..,"messages":[{"role":"user","content":..}],"temperature":0}
     * Parses: choices[0].message.content
     */
    private suspend fun callOpenAiCompatible(
        baseUrl: String,
        model: String,
        apiKey: String,
        prompt: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val root = baseUrl.trimEnd('/')
            val url = if (root.endsWith("/chat/completions")) root else "$root/chat/completions"

            val messageObj = JsonObject().apply {
                addProperty("role", "user")
                addProperty("content", prompt)
            }
            val messages = com.google.gson.JsonArray().apply { add(messageObj) }
            val bodyJson = JsonObject().apply {
                addProperty("model", model)
                add("messages", messages)
                addProperty("temperature", 0)
            }

            val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer $apiKey")
                .post(gson.toJson(bodyJson).toRequestBody("application/json".toMediaType()))
                .build()

            Log.d(TAG, "OpenAI POST $url model=$model")
            httpClient.newCall(request).execute().use { response ->
                Log.d(TAG, "OpenAI HTTP ${response.code}")
                if (!response.isSuccessful) return@withContext null
                val respBody = response.body?.string() ?: return@withContext null

                val json = gson.fromJson(respBody, JsonObject::class.java) ?: return@withContext null
                val choices = json.getAsJsonArray("choices") ?: return@withContext null
                if (choices.size() == 0) return@withContext null
                val message = choices[0].asJsonObject.getAsJsonObject("message") ?: return@withContext null
                return@withContext message.get("content")?.asString
            }
        } catch (e: Exception) {
            Log.e(TAG, "OpenAI call failed: ${e.message}")
            null
        }
    }

    /**
     * Gemini-compatible REST endpoint:
     * POST {baseUrl}/v1beta/models/{model}:generateContent?key={apiKey}
     * Parses: candidates[0].content.parts[0].text
     */
    private suspend fun callGeminiRest(
        baseUrl: String,
        model: String,
        apiKey: String,
        prompt: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val url = "$baseUrl/v1beta/models/$model:generateContent?key=$apiKey"

            val part = JsonObject().apply { addProperty("text", prompt) }
            val partsArray = com.google.gson.JsonArray().apply { add(part) }
            val contentObj = JsonObject().apply { add("parts", partsArray) }
            val contentsArray = com.google.gson.JsonArray().apply { add(contentObj) }
            val bodyJson = JsonObject().apply { add("contents", contentsArray) }

            val request = Request.Builder()
                .url(url)
                .post(gson.toJson(bodyJson).toRequestBody("application/json".toMediaType()))
                .build()

            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val respBody = response.body?.string() ?: return@withContext null

                val root = gson.fromJson(respBody, JsonObject::class.java) ?: return@withContext null
                val candidates = root.getAsJsonArray("candidates") ?: return@withContext null
                if (candidates.size() == 0) return@withContext null
                val content = candidates[0].asJsonObject.getAsJsonObject("content") ?: return@withContext null
                val parts = content.getAsJsonArray("parts") ?: return@withContext null
                if (parts.size() == 0) return@withContext null
                return@withContext parts[0].asJsonObject.get("text")?.asString
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun mapToAppCategory(aiCategory: String): String {
        val cat = aiCategory.lowercase()
        return when {
            cat.contains("makan") -> "Makanan"
            cat.contains("transp") -> "Transportasi"
            cat.contains("belanja") || cat.contains("shop") -> "Belanja"
            cat.contains("tagih") || cat.contains("bill") || cat.contains("sub") -> "Tagihan"
            cat.contains("hibur") || cat.contains("entert") -> "Hiburan"
            cat.contains("sehat") || cat.contains("health") -> "Kesehatan"
            cat.contains("didik") || cat.contains("edu") -> "Pendidikan"
            cat.contains("gaji") || cat.contains("salary") -> "Gaji"
            cat.contains("tabung") || cat.contains("sav") -> "Tabungan"
            cat.contains("invest") -> "Investasi"
            cat.contains("transfer masuk") || cat.contains("inbound") -> "Transfer Masuk"
            cat.contains("transfer keluar") || cat.contains("outbound") -> "Transfer Keluar"
            else -> "Lainnya"
        }
    }
    
    suspend fun predictCategory(merchantName: String, amount: Long): String {
        val result = predictTransaction("Payment to $merchantName for Rp$amount")
        return result.category
    }

    fun isPotentialTransaction(text: String): Boolean {
        return ruleEngine.isRealTransaction(text)
    }

    fun predictLocally(text: String, packageName: String? = null): PredictionResult {
        return ruleEngine.predict(text, packageName)
    }
}

data class AiPredictionResponse(
    val isTransaction: Boolean,
    val merchant: String,
    val category: String,
    val confidence: Float
)
