package com.yusuffdllh.smartfinance.data.sync

import android.util.Log
import com.yusuffdllh.smartfinance.domain.repository.BudgetRepository
import com.yusuffdllh.smartfinance.domain.repository.ScheduledBillRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Restores a user's cloud data (Firestore) into the local Room cache after login.
 *
 * Room is the single source of truth for the UI; this only pulls remote rows in so the
 * reactive Flows already wired to the screens light up automatically. No UI changes needed.
 */
@Singleton
class DataSyncManager @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val budgetRepository: BudgetRepository,
    private val scheduledBillRepository: ScheduledBillRepository
) {
    /**
     * Pulls all per-user collections from Firestore into Room. Failures are non-fatal
     * (e.g. offline or Firestore API disabled) — the app keeps working with local data.
     */
    suspend fun restoreUserData(userId: String) {
        if (userId.isEmpty()) return
        transactionRepository.restoreFromFirestore(userId)
        budgetRepository.restoreFromFirestore(userId)
        scheduledBillRepository.restoreFromFirestore(userId)
        Log.i(TAG, "restoreUserData completed for $userId")
    }

    private companion object {
        const val TAG = "DataSyncManager"
    }
}
