package com.yusuffdllh.smartfinance.domain.repository

import com.yusuffdllh.smartfinance.data.local.entity.ScheduledBillEntity
import kotlinx.coroutines.flow.Flow

interface ScheduledBillRepository {
    fun getBills(): Flow<List<ScheduledBillEntity>>
    suspend fun addBill(bill: ScheduledBillEntity): Result<Unit>
    suspend fun deleteBill(bill: ScheduledBillEntity): Result<Unit>
    suspend fun updateBill(bill: ScheduledBillEntity): Result<Unit>

    /** Pulls the user's scheduled bills from Firestore into the local Room cache. */
    suspend fun restoreFromFirestore(userId: String): Result<Unit>
}
