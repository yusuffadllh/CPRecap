package com.yusuffdllh.smartfinance.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.yusuffdllh.smartfinance.data.local.AppDatabase
import com.yusuffdllh.smartfinance.data.local.UserPreferences
import com.yusuffdllh.smartfinance.data.model.User
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val database: AppDatabase,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                val isGoogle = firebaseUser.providerData.any { it.providerId == "google.com" }
                // Initial basic user info from Auth
                val baseUser = User(
                    id = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    photoUrl = firebaseUser.photoUrl?.toString() ?: "",
                    isGoogleLinked = isGoogle
                )
                
                // Fetch extra details from Firestore
                firestore.collection("users").document(firebaseUser.uid)
                    .addSnapshotListener { snapshot, _ ->
                        if (snapshot != null && snapshot.exists()) {
                            val user = baseUser.copy(
                                phone = snapshot.getString("phone") ?: "",
                                birthDate = snapshot.getString("birthDate") ?: "",
                                gender = snapshot.getString("gender") ?: ""
                            )
                            trySend(user)
                        } else {
                            trySend(baseUser)
                        }
                    }
            } else {
                trySend(null)
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "signInWithEmail failed", e)
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String, name: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            // Update profile with name
            val user = firebaseAuth.currentUser
            user?.updateProfile(
                com.google.firebase.auth.userProfileChangeRequest {
                    displayName = name
                }
            )?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "signUpWithEmail failed", e)
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "signInWithGoogle failed", e)
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            withContext(kotlinx.coroutines.Dispatchers.IO) {
                database.clearAllTables()
                userPreferences.clear()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "signOut failed", e)
            Result.failure(e)
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun getGoogleAccountName(): String? {
        val gmailAccount = userPreferences.gmailAccountName.first()
        return gmailAccount.ifBlank { firebaseAuth.currentUser?.email }
    }

    override suspend fun linkGoogleAccount(idToken: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            user.linkWithCredential(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "linkGoogleAccount failed", e)
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(name: String, photoUri: String?): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
            val request = com.google.firebase.auth.userProfileChangeRequest {
                displayName = name
                photoUri?.let { this.photoUri = android.net.Uri.parse(it) }
            }
            user.updateProfile(request).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updateProfile failed", e)
            Result.failure(e)
        }
    }

    override suspend fun updateEmail(newEmail: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
            user.verifyBeforeUpdateEmail(newEmail).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updateEmail failed", e)
            Result.failure(e)
        }
    }

    override suspend fun updateFullProfile(user: User): Result<Unit> {
        return try {
            val firebaseUser = firebaseAuth.currentUser ?: throw Exception("User not logged in")
            
            // 1. Update Auth profile (Name)
            val authRequest = com.google.firebase.auth.userProfileChangeRequest {
                displayName = user.name
            }
            firebaseUser.updateProfile(authRequest).await()
            
            // 2. Update Firestore (Phone, BirthDate, Gender)
            val data = mapOf(
                "name" to user.name,
                "phone" to user.phone,
                "birthDate" to user.birthDate,
                "gender" to user.gender
            )
            firestore.collection("users").document(user.id).set(data, com.google.firebase.firestore.SetOptions.merge()).await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updateFullProfile failed", e)
            Result.failure(e)
        }
    }

    override suspend fun changePassword(newPassword: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")
            user.updatePassword(newPassword).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "changePassword failed", e)
            Result.failure(e)
        }
    }

    private companion object {
        const val TAG = "AuthRepository"
    }
}
