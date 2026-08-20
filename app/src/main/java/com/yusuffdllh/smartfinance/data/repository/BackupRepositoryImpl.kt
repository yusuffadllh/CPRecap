package com.yusuffdllh.smartfinance.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.yusuffdllh.smartfinance.data.local.dao.BudgetDao
import com.yusuffdllh.smartfinance.data.local.dao.CategoryDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDao
import com.yusuffdllh.smartfinance.data.model.BackupData
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.BackupRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transactionDao: TransactionDao,
    private val budgetDao: BudgetDao,
    private val categoryDao: CategoryDao,
    private val authRepository: AuthRepository,
    private val gson: Gson
) : BackupRepository {

    override suspend fun exportData(): Result<String> {
        return try {
            val user = authRepository.currentUser.first() ?: throw Exception("User not logged in")
            val data = BackupData(
                transactions = transactionDao.getTransactionsByUserId(user.id).first(),
                budgets = budgetDao.getBudgetsByUserId(user.id).first(),
                categories = categoryDao.getAllCategories().first()
            )
            Result.success(gson.toJson(data))
        } catch (e: Exception) {
            Log.e(TAG, "exportData failed", e)
            Result.failure(e)
        }
    }

    override suspend fun importData(json: String): Result<Unit> {
        return try {
            val data = gson.fromJson(json, BackupData::class.java)
            data.transactions.forEach { transactionDao.insertTransaction(it) }
            data.budgets.forEach { budgetDao.insertBudget(it) }
            data.categories.forEach { categoryDao.insertCategory(it) }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "importData failed", e)
            Result.failure(e)
        }
    }

    override suspend fun importDataFromUri(uri: Uri): Result<Unit> {
        return try {
            val json = context.contentResolver.openInputStream(uri)?.use {
                it.bufferedReader().readText()
            } ?: throw Exception("Failed to open input stream")
            importData(json)
        } catch (e: Exception) {
            Log.e(TAG, "importDataFromUri failed", e)
            Result.failure(e)
        }
    }

    private companion object {
        const val TAG = "BackupRepository"
    }
}
