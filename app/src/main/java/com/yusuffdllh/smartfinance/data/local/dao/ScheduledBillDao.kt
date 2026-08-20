package com.yusuffdllh.smartfinance.data.local.dao

import androidx.room.*
import com.yusuffdllh.smartfinance.data.local.entity.ScheduledBillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduledBillDao {
    @Query("SELECT * FROM scheduled_bills WHERE userId = :userId")
    fun getBillsByUserId(userId: String): Flow<List<ScheduledBillEntity>>

    @Query("SELECT * FROM scheduled_bills WHERE isAutoPaid = 1")
    suspend fun getAllAutoPaidBills(): List<ScheduledBillEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: ScheduledBillEntity): Long

    @Delete
    suspend fun deleteBill(bill: ScheduledBillEntity)

    @Update
    suspend fun updateBill(bill: ScheduledBillEntity)
}
