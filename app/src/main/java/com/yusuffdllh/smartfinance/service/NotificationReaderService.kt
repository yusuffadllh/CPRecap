package com.yusuffdllh.smartfinance.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import com.yusuffdllh.smartfinance.MainActivity
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReaderService : NotificationListenerService() {

    @Inject lateinit var aiService: AiService
    @Inject lateinit var ruleEngine: RuleEngine
    @Inject lateinit var authRepository: AuthRepository
    @Inject lateinit var transactionRepository: TransactionRepository
    @Inject lateinit var userPreferences: com.yusuffdllh.smartfinance.data.local.UserPreferences
    @Inject lateinit var gmailHelper: GmailHelper

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val channelId = "transaction_alerts"

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(channelId, "Transaction Alerts", NotificationManager.IMPORTANCE_DEFAULT)
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
    }

    private fun showTransactionAlert(prediction: PredictionResult, isDraft: Boolean) {
        val amountStr = "%,d".format(prediction.amount).replace(',', '.')
        val title = prediction.category
        val body = if (isDraft) "Konfirmasi: Rp $amountStr \u2022 ${prediction.merchant}"
                   else "Rp $amountStr \u2022 ${prediction.merchant}"

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", "transactions")
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(1, builder.build())
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val notification = sbn?.notification ?: return
        val pkg = sbn.packageName ?: return
        if (notification.flags and Notification.FLAG_GROUP_SUMMARY != 0) return

        val extras = notification.extras
        val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
        val text = extras.getString(Notification.EXTRA_TEXT) ?: ""
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""
        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?: ""
        val infoText = extras.getCharSequence(Notification.EXTRA_INFO_TEXT)?.toString() ?: ""
        val fullContent = "$title $text $bigText $subText $infoText".trim()
        if (fullContent.isEmpty()) return

        val supportedApps = buildList {
            addAll(
                listOf(
                    "com.dana", "com.gopay.app", "com.gojek.app", "com.shopee.id",
                    "id.ovo.android", "com.telkomsel.linkaja", "com.doku.wallet",
                    "com.bt.bclient", "com.jago.jago", "com.neobank.indonesia",
                    "id.co.anypay.blu", "id.co.bca.mobile", "id.co.bca.blue",
                    "com.bri.brimo", "com.bankmandiri.livin", "com.bni.mbanking",
                    "id.co.bni.newmobile", "com.google.android.gm"
                )
            )
            // DEBUG-ONLY: allow notifications posted via `adb`/shell so the
            // notification-bar pipeline can be tested end-to-end without a real
            // bank app installed. Never included in release builds.
            if (com.yusuffdllh.smartfinance.BuildConfig.DEBUG) {
                add("com.android.shell")
            }
        }

        // Never process our own feedback notifications (prevents a self-loop).
        if (pkg == packageName) return

        // Only proceed for supported financial apps. Random apps that merely
        // mention "Rp" (e-commerce ads, chats, etc.) must be ignored entirely.
        if (!supportedApps.contains(pkg)) return
        Log.d(TAG, "Notification accepted from pkg=$pkg")

        // Early hard filter: drop promotions and failed/cancelled transactions
        // right away so they never reach the AI or the database.
        if (ruleEngine.isPromotion(fullContent) || ruleEngine.isFailure(fullContent)) return

        if (pkg == "com.google.android.gm") {
            serviceScope.launch {
                kotlinx.coroutines.delay(800)
                val body = gmailHelper.fetchFullEmailBody(fullContent)
                val resolved = body ?: fullContent
                // Re-check the fully expanded email body for promo content.
                if (ruleEngine.isPromotion(resolved) || ruleEngine.isFailure(resolved)) return@launch
                processIncomingNotification(resolved, pkg)
            }
        } else {
            processIncomingNotification(fullContent, pkg)
        }
    }

    private fun processIncomingNotification(content: String, pkg: String? = null) {
        serviceScope.launch {
            Log.d(TAG, "processIncoming pkg=$pkg content=\"${content.take(80)}\"")
            val user = authRepository.currentUser.first() ?: run {
                Log.d(TAG, "No logged-in user; skipping")
                return@launch
            }
            val prediction = aiService.predictTransaction(content, pkg)
            Log.d(TAG, "prediction isTx=${prediction.isTransaction} merchant=\"${prediction.merchant}\" cat=${prediction.category} amount=${prediction.amount} type=${prediction.type} conf=${prediction.confidence}")
            if (!prediction.isTransaction || prediction.amount <= 0) return@launch

            val min = userPreferences.minDailyRp.first().toLongOrNull() ?: 0L
            val max = userPreferences.maxDailyRp.first().toLongOrNull() ?: Long.MAX_VALUE
            if (prediction.amount !in min..max) return@launch

            if (prediction.confidence >= 0.75f) {
                val tx = TransactionEntity(
                    userId = user.id, title = prediction.merchant, amount = prediction.amount,
                    category = prediction.category, date = System.currentTimeMillis(),
                    type = prediction.type, note = "Sinkronisasi Notifikasi"
                )
                val result = transactionRepository.addTransaction(tx)
                Log.d(TAG, "AUTO-SAVED tx (conf>=0.75) success=${result.isSuccess}")
                if (result.isSuccess) showTransactionAlert(prediction, false)
            } else {
                val draf = TransactionDraftEntity(
                    userId = user.id, merchant = prediction.merchant, amount = prediction.amount,
                    date = System.currentTimeMillis(), reference = content.take(50),
                    type = prediction.type, category = prediction.category
                )
                val result = transactionRepository.addDraft(draf)
                Log.d(TAG, "DRAFT created (conf<0.75) success=${result.isSuccess}")
                if (result.isSuccess) showTransactionAlert(prediction, true)
            }
        }
    }

    private companion object {
        const val TAG = "NotifReader"
    }
}