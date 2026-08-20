package com.yusuffdllh.smartfinance.presentation.setting.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.model.User
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Idle)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    val user: StateFlow<User?> = authRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun toggleEditMode(enabled: Boolean) {
        _isEditMode.value = enabled
    }

    fun updateProfile(name: String, photoUri: String? = null) {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            val result = authRepository.updateProfile(name, photoUri)
            if (result.isSuccess) {
                _uiState.value = AccountUiState.Success("Profil diperbarui")
            } else {
                _uiState.value = AccountUiState.Error(result.exceptionOrNull()?.message ?: "Gagal memperbarui profil")
            }
        }
    }

    fun updateFullProfile(user: User) {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            val result = authRepository.updateFullProfile(user)
            if (result.isSuccess) {
                _uiState.value = AccountUiState.Success("Profil diperbarui")
            } else {
                _uiState.value = AccountUiState.Error(result.exceptionOrNull()?.message ?: "Gagal memperbarui profil")
            }
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            val result = authRepository.updateEmail(newEmail)
            if (result.isSuccess) {
                _uiState.value = AccountUiState.Success("Email verifikasi dikirim")
            } else {
                _uiState.value = AccountUiState.Error(result.exceptionOrNull()?.message ?: "Gagal memperbarui email")
            }
        }
    }

    fun changePassword(newPassword: String) {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            val result = authRepository.changePassword(newPassword)
            if (result.isSuccess) {
                _uiState.value = AccountUiState.Success("Kata sandi diperbarui")
            } else {
                _uiState.value = AccountUiState.Error(result.exceptionOrNull()?.message ?: "Gagal memperbarui kata sandi")
            }
        }
    }

    fun resetState() {
        _uiState.value = AccountUiState.Idle
    }
}

sealed class AccountUiState {
    object Idle : AccountUiState()
    object Loading : AccountUiState()
    data class Success(val message: String) : AccountUiState()
    data class Error(val message: String) : AccountUiState()
}
