package com.yusuffdllh.smartfinance.data.local

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yusuffdllh.smartfinance.utils.CryptoManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dbKeyStore: DataStore<Preferences> by preferencesDataStore(name = "db_key_prefs")

/**
 * Provides the SQLCipher passphrase for the Room database.
 *
 * The passphrase is a random 32-byte value generated once on first launch and
 * stored **encrypted** (via [CryptoManager], AES-256/GCM with a key held in the
 * Android Keystore) inside a dedicated DataStore. It never appears in plaintext
 * on disk and is not hard-coded in the APK.
 */
@Singleton
class DatabasePassphraseProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cryptoManager: CryptoManager
) {

    /**
     * Returns the raw passphrase bytes for SQLCipher, generating and persisting
     * a new one on first access. Called during DB construction, so it runs
     * synchronously via [runBlocking] on a background-provided thread.
     */
    fun getOrCreatePassphrase(): ByteArray = runBlocking {
        val stored = context.dbKeyStore.data.first()[PASSPHRASE_KEY]
        val plain = cryptoManager.decrypt(stored ?: "")
        if (plain.isNotEmpty()) {
            Base64.decode(plain, Base64.NO_WRAP)
        } else {
            val newKey = ByteArray(PASSPHRASE_SIZE_BYTES).also { SecureRandom().nextBytes(it) }
            val encoded = Base64.encodeToString(newKey, Base64.NO_WRAP)
            context.dbKeyStore.edit { it[PASSPHRASE_KEY] = cryptoManager.encrypt(encoded) }
            newKey
        }
    }

    private companion object {
        val PASSPHRASE_KEY = stringPreferencesKey("db_passphrase")
        const val PASSPHRASE_SIZE_BYTES = 32
    }
}
