package com.yusuffdllh.smartfinance.presentation.setting.backup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.domain.repository.BackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val backupRepository: BackupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BackupUiState>(BackupUiState.Idle)
    val uiState: StateFlow<BackupUiState> = _uiState.asStateFlow()

    fun exportData() {
        viewModelScope.launch {
            _uiState.value = BackupUiState.Loading
            val result = backupRepository.exportData()
            if (result.isSuccess) {
                _uiState.value = BackupUiState.ExportSuccess(result.getOrThrow())
            } else {
                _uiState.value = BackupUiState.Error(result.exceptionOrNull()?.message ?: "Export failed")
            }
        }
    }

    fun importData(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = BackupUiState.Loading
            val result = backupRepository.importDataFromUri(uri)
            if (result.isSuccess) {
                _uiState.value = BackupUiState.ImportSuccess
            } else {
                _uiState.value = BackupUiState.Error(result.exceptionOrNull()?.message ?: "Import failed")
            }
        }
    }

    fun resetState() {
        _uiState.value = BackupUiState.Idle
    }
}

sealed class BackupUiState {
    object Idle : BackupUiState()
    object Loading : BackupUiState()
    data class ExportSuccess(val json: String) : BackupUiState()
    object ImportSuccess : BackupUiState()
    data class Error(val message: String) : BackupUiState()
}
