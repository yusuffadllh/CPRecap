package com.yusuffdllh.smartfinance.service

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    /** Unique name for the on-demand (manual) Gmail sync. */
    const val GMAIL_SYNC_NOW_WORK = "GmailSyncNow"

    fun scheduleGmailSync(context: Context) {
        val syncRequest = PeriodicWorkRequestBuilder<GmailSyncWorker>(1, TimeUnit.HOURS)
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "GmailSync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    /**
     * Triggers an immediate, one-time Gmail sync (used by the "Sinkronkan
     * Sekarang" button). REPLACE policy so repeated taps restart a fresh run.
     * Returns the unique work name so callers can observe its WorkInfo.
     */
    fun syncGmailNow(context: Context): String {
        val request = OneTimeWorkRequestBuilder<GmailSyncWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            GMAIL_SYNC_NOW_WORK,
            ExistingWorkPolicy.REPLACE,
            request
        )
        return GMAIL_SYNC_NOW_WORK
    }

    fun scheduleBillChecks(context: Context) {
        val billRequest = PeriodicWorkRequestBuilder<ScheduledBillWorker>(12, TimeUnit.HOURS)
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "BillCheck",
            ExistingPeriodicWorkPolicy.KEEP,
            billRequest
        )
    }
}
