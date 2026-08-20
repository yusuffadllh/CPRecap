package com.yusuffdllh.smartfinance.domain.repository

import com.yusuffdllh.smartfinance.data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmail(email: String, password: String, name: String): Result<Unit>
    suspend fun signInWithGoogle(idToken: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getGoogleAccountName(): String?
    suspend fun linkGoogleAccount(idToken: String): Result<Unit>
    suspend fun updateProfile(name: String, photoUri: String?): Result<Unit>
    suspend fun updateEmail(newEmail: String): Result<Unit>
    suspend fun updateFullProfile(user: User): Result<Unit>
    suspend fun changePassword(newPassword: String): Result<Unit>
}
