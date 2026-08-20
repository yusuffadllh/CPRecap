package com.yusuffdllh.smartfinance.domain.repository

import android.net.Uri

interface BackupRepository {
    suspend fun exportData(): Result<String> // Returns JSON string or file path
    suspend fun importData(json: String): Result<Unit>
    suspend fun importDataFromUri(uri: Uri): Result<Unit>
}
