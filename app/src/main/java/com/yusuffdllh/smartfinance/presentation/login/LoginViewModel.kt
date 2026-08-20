package com.yusuffdllh.smartfinance.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.sync.DataSyncManager
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataSyncManager: DataSyncManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun setError(message: String) {
        _uiState.value = LoginUiState.Error(message)
    }

    fun setLoading() {
        _uiState.value = LoginUiState.Loading
    }

    fun signIn(email: String, password: String) {
        if (!isValidEmail(email)) {
            _uiState.value = LoginUiState.Error("Email tidak valid")
            return
        }
        if (password.length < 6) {
            _uiState.value = LoginUiState.Error("Password minimal 6 karakter")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = authRepository.signInWithEmail(email, password)
            if (result.isSuccess) {
                restoreUserData()
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error(result.exceptionOrNull()?.message ?: "Gagal masuk")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = authRepository.signInWithGoogle(idToken)
            if (result.isSuccess) {
                restoreUserData()
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    /** Pulls the freshly-authenticated user's cloud data into Room before showing the dashboard. */
    private suspend fun restoreUserData() {
        val userId = authRepository.currentUser.first()?.id ?: return
        dataSyncManager.restoreUserData(userId)
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
