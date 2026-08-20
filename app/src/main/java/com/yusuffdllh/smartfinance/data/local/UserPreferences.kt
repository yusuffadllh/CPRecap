package com.yusuffdllh.smartfinance.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val GEMEINI_API_KEY = stringPreferencesKey("gemini_api_key")
    private val AI_BASE_URL = stringPreferencesKey("ai_base_url")
    private val AI_MODEL = stringPreferencesKey("ai_model")
    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    
    private val BANK_READER_ENABLED = booleanPreferencesKey("bank_reader_enabled")
    private val EMAIL_SYNC_ENABLED = booleanPreferencesKey("email_sync_enabled")
    private val BUDGET_WARNING_ENABLED = booleanPreferencesKey("budget_warning_enabled")
    
    private val MIN_DAILY_RP = stringPreferencesKey("min_daily_rp")
    private val MAX_DAILY_RP = stringPreferencesKey("max_daily_rp")
    private val LAST_DETECTED_PACKAGE = stringPreferencesKey("last_detected_package")

    private val GMAIL_AUTHORIZED = booleanPreferencesKey("gmail_authorized")
    private val GMAIL_ACCOUNT_NAME = stringPreferencesKey("gmail_account_name")

    val geminiApiKey: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { it[GEMEINI_API_KEY] ?: "" }

    val aiBaseUrl: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { it[AI_BASE_URL] ?: DEFAULT_AI_BASE_URL }

    val aiModel: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { it[AI_MODEL] ?: DEFAULT_AI_MODEL }

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { it[IS_DARK_MODE] ?: true }

    val bankReaderEnabled: Flow<Boolean> = context.dataStore.data.map { it[BANK_READER_ENABLED] ?: false }
    val emailSyncEnabled: Flow<Boolean> = context.dataStore.data.map { it[EMAIL_SYNC_ENABLED] ?: false }
    val budgetWarningEnabled: Flow<Boolean> = context.dataStore.data.map { it[BUDGET_WARNING_ENABLED] ?: true }
    
    val minDailyRp: Flow<String> = context.dataStore.data.map { it[MIN_DAILY_RP] ?: "" }
    val maxDailyRp: Flow<String> = context.dataStore.data.map { it[MAX_DAILY_RP] ?: "" }
    val lastDetectedPackage: Flow<String> = context.dataStore.data.map { it[LAST_DETECTED_PACKAGE] ?: "None" }

    val gmailAuthorized: Flow<Boolean> = context.dataStore.data.map { it[GMAIL_AUTHORIZED] ?: false }
    val gmailAccountName: Flow<String> = context.dataStore.data.map { it[GMAIL_ACCOUNT_NAME] ?: "" }

    suspend fun saveGeminiApiKey(apiKey: String) {
        context.dataStore.edit { it[GEMEINI_API_KEY] = apiKey }
    }

    /**
     * Saves the AI (Gemini-compatible) configuration. An empty [baseUrl] resets
     * it to the official Google endpoint; an empty [model] resets to default.
     */
    suspend fun saveAiConfig(baseUrl: String, apiKey: String, model: String) {
        context.dataStore.edit {
            it[AI_BASE_URL] = baseUrl.trim().ifEmpty { DEFAULT_AI_BASE_URL }.trimEnd('/')
            it[GEMEINI_API_KEY] = apiKey.trim()
            it[AI_MODEL] = model.trim().ifEmpty { DEFAULT_AI_MODEL }
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[IS_DARK_MODE] = enabled }
    }

    suspend fun setBankReaderEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BANK_READER_ENABLED] = enabled }
    }

    suspend fun setEmailSyncEnabled(enabled: Boolean) {
        context.dataStore.edit { it[EMAIL_SYNC_ENABLED] = enabled }
    }

    suspend fun setBudgetWarningEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BUDGET_WARNING_ENABLED] = enabled }
    }

    suspend fun setDailyRange(min: String, max: String) {
        context.dataStore.edit { 
            it[MIN_DAILY_RP] = min
            it[MAX_DAILY_RP] = max
        }
    }

    suspend fun setLastDetectedPackage(pkg: String) {
        context.dataStore.edit { it[LAST_DETECTED_PACKAGE] = pkg }
    }

    suspend fun setGmailAuthorized(authorized: Boolean, accountName: String) {
        context.dataStore.edit {
            it[GMAIL_AUTHORIZED] = authorized
            it[GMAIL_ACCOUNT_NAME] = accountName
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    companion object {
        const val DEFAULT_AI_BASE_URL = "https://generativelanguage.googleapis.com"
        const val DEFAULT_AI_MODEL = "gemini-1.5-flash"
    }
}
