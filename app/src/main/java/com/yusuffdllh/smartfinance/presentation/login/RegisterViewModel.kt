package com.yusuffdllh.smartfinance.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun signUp(email: String, password: String, name: String) {
        if (name.isEmpty()) {
            _uiState.value = RegisterUiState.Error("Nama tidak boleh kosong")
            return
        }
        if (!isValidEmail(email)) {
            _uiState.value = RegisterUiState.Error("Email tidak valid")
            return
        }
        if (password.length < 6) {
            _uiState.value = RegisterUiState.Error("Password minimal 6 karakter")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            val result = authRepository.signUpWithEmail(email, password, name)
            if (result.isSuccess) {
                _uiState.value = RegisterUiState.Success
            } else {
                _uiState.value = RegisterUiState.Error(result.exceptionOrNull()?.message ?: "Terjadi kesalahan")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}
