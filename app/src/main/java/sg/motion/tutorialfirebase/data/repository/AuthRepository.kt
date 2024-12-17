package sg.motion.tutorialfirebase.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

// Authentication Handler (from previous example)
class AuthRepository(private val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Email and Password Authentication
    suspend fun signInWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(authResult.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Email and Password Registration
    suspend fun registerWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(authResult.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Check if user is currently logged in
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    // Get current user ID
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    // Logout
    fun logout() {
        auth.signOut()
    }
}