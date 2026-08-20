package com.yusuffdllh.smartfinance.service

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.Message
import com.google.api.services.gmail.model.MessagePart
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GmailHelper @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
) {
    private val jsonFactory = GsonFactory.getDefaultInstance()
    private val transport = NetHttpTransport()

    suspend fun fetchFullEmailBody(snippet: String): String? = withContext(Dispatchers.IO) {
        try {
            val accountName = authRepository.getGoogleAccountName() ?: return@withContext null
            val credential = GoogleAccountCredential.usingOAuth2(context, listOf(GmailScopes.GMAIL_READONLY))
            credential.selectedAccountName = accountName

            val service = Gmail.Builder(transport, jsonFactory, credential)
                .setApplicationName("CPRecap")
                .build()

            val query = snippet.take(30).replace("'", "").replace("\"", "")
            val listResponse = service.users().messages().list("me")
                .setQ(query)
                .setMaxResults(1L)
                .execute()

            val messages = listResponse.messages ?: return@withContext null
            if (messages.isEmpty()) return@withContext null

            val fullMessage = service.users().messages().get("me", messages[0].id)
                .setFormat("full")
                .execute()

            return@withContext extractTextFromMessage(fullMessage)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getLatestTransactions(): List<String> = withContext(Dispatchers.IO) {
        try {
            val accountName = authRepository.getGoogleAccountName() ?: return@withContext emptyList()
            val credential = GoogleAccountCredential.usingOAuth2(context, listOf(GmailScopes.GMAIL_READONLY))
            credential.selectedAccountName = accountName

            val service = Gmail.Builder(transport, jsonFactory, credential)
                .setApplicationName("CPRecap")
                .build()

            val listResponse = service.users().messages().list("me")
                .setQ("label:INBOX (receipt OR payment OR \"bukti transfer\" OR \"berhasil\" OR \"sukses\" OR \"purchase\")")
                .setMaxResults(10L)
                .execute()

            val messages = listResponse.messages ?: return@withContext emptyList()
            
            return@withContext messages.mapNotNull { msg ->
                try {
                    val details = service.users().messages().get("me", msg.id).setFormat("full").execute()
                    extractTextFromMessage(details)
                } catch (e: Exception) { null }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun extractTextFromMessage(message: Message): String {
        val sb = StringBuilder()
        val payload = message.payload
        
        sb.append(message.snippet ?: "").append("\n")
        
        fun processParts(parts: List<MessagePart>?) {
            parts?.forEach { part ->
                if (part.mimeType == "text/plain") {
                    part.body.data?.let {
                        sb.append(String(android.util.Base64.decode(it, android.util.Base64.URL_SAFE)))
                    }
                } else if (part.parts != null) {
                    processParts(part.parts)
                }
            }
        }
        
        processParts(payload.parts)
        
        if (payload.mimeType == "text/plain") {
            payload.body.data?.let {
                sb.append(String(android.util.Base64.decode(it, android.util.Base64.URL_SAFE)))
            }
        }
        
        return sb.toString()
    }
}
