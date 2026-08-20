package com.yusuffdllh.smartfinance.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class GmailSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val aiService: AiService,
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository,
    private val gmailHelper: GmailHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val user = authRepository.currentUser.first() ?: run {
                android.util.Log.d(TAG, "No user; skip Gmail sync")
                return Result.success()
            }

            val messages = gmailHelper.getLatestTransactions()
            android.util.Log.d(TAG, "Gmail sync: fetched ${messages.size} candidate email(s)")

            messages.forEach { content ->
                val prediction = aiService.predictTransaction(content, "com.google.android.gm")
                android.util.Log.d(TAG, "Gmail prediction isTx=${prediction.isTransaction} merchant=\"${prediction.merchant}\" amount=${prediction.amount} conf=${prediction.confidence}")

                if (prediction.isTransaction && prediction.amount > 0) {
                    if (prediction.confidence >= 0.75f) {
                        val transaction = TransactionEntity(
                            userId = user.id,
                            title = prediction.merchant,
                            amount = prediction.amount,
                            category = prediction.category,
                            date = System.currentTimeMillis(),
                            type = prediction.type,
                            note = "Sinkronisasi Email"
                        )
                        transactionRepository.addTransaction(transaction)
                    } else {
                        val draft = TransactionDraftEntity(
                            userId = user.id,
                            merchant = prediction.merchant,
                            amount = prediction.amount,
                            date = System.currentTimeMillis(),
                            reference = "Email: ${prediction.merchant}",
                            type = prediction.type,
                            category = prediction.category
                        )
                        transactionRepository.addDraft(draft)
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Gmail sync failed: ${e.message}")
            Result.failure()
        }
    }

    private companion object {
        const val TAG = "GmailSync"
    }
}
