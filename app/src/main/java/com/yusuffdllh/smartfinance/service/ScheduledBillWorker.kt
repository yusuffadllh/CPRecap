package com.yusuffdllh.smartfinance.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yusuffdllh.smartfinance.data.local.dao.ScheduledBillDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDao
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class ScheduledBillWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val billDao: ScheduledBillDao,
    private val transactionDao: TransactionDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val bills = billDao.getAllAutoPaidBills()
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val monthYear = SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(calendar.time)

            bills.forEach { bill ->
                if (bill.dueDate == day && bill.lastGeneratedMonthYear != monthYear) {
                    val tx = TransactionEntity(
                        userId = bill.userId,
                        title = "Bill: ${bill.name}",
                        amount = bill.amount,
                        category = bill.category,
                        date = System.currentTimeMillis(),
                        type = "EXPENSE",
                        note = "Scheduled payment"
                    )
                    transactionDao.insertTransaction(tx)
                    billDao.updateBill(bill.copy(lastGeneratedMonthYear = monthYear))
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
