package com.yusuffdllh.smartfinance.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.yusuffdllh.smartfinance.data.local.dao.ScheduledBillDao
import com.yusuffdllh.smartfinance.data.local.entity.ScheduledBillEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.ScheduledBillRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduledBillRepositoryImpl @Inject constructor(
    private val billDao: ScheduledBillDao,
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore,
    private val externalScope: CoroutineScope
) : ScheduledBillRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getBills(): Flow<List<ScheduledBillEntity>> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user != null) {
                billDao.getBillsByUserId(user.id)
            } else {
                flowOf(emptyList())
            }
        }
    }

    override suspend fun addBill(bill: ScheduledBillEntity): Result<Unit> {
        return try {
            val id = billDao.insertBill(bill)
            val updated = bill.copy(id = id)
            syncToFirestore(updated)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "addBill failed", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteBill(bill: ScheduledBillEntity): Result<Unit> {
        return try {
            billDao.deleteBill(bill)
            externalScope.launch {
                try {
                    firestore.collection("users")
                        .document(bill.userId)
                        .collection("scheduled_bills")
                        .document(bill.id.toString())
                        .delete()
                        .await()
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to delete scheduled bill from Firestore", e)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "deleteBill failed", e)
            Result.failure(e)
        }
    }

    override suspend fun updateBill(bill: ScheduledBillEntity): Result<Unit> {
        return try {
            billDao.updateBill(bill)
            syncToFirestore(bill)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updateBill failed", e)
            Result.failure(e)
        }
    }

    private fun syncToFirestore(bill: ScheduledBillEntity) {
        if (bill.userId.isEmpty()) return
        externalScope.launch {
            try {
                firestore.collection("users")
                    .document(bill.userId)
                    .collection("scheduled_bills")
                    .document(bill.id.toString())
                    .set(bill)
                    .await()
                billDao.updateBill(bill.copy(isSynced = true))
            } catch (e: Exception) {
                Log.w(TAG, "Failed to sync scheduled bill to Firestore", e)
            }
        }
    }

    override suspend fun restoreFromFirestore(userId: String): Result<Unit> {
        if (userId.isEmpty()) return Result.success(Unit)
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("scheduled_bills")
                .get()
                .await()
            val remote = snapshot.documents.mapNotNull { it.toObject(ScheduledBillEntity::class.java) }
            remote.forEach { billDao.insertBill(it.copy(isSynced = true)) }
            Log.i(TAG, "Restored ${remote.size} scheduled bills from Firestore")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w(TAG, "restoreFromFirestore failed", e)
            Result.failure(e)
        }
    }

    private companion object {
        const val TAG = "ScheduledBillRepo"
    }
}
