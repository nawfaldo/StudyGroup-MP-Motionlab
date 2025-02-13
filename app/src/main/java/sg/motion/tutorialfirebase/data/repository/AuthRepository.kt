package sg.motion.tutorialfirebase.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {
    // TODO : init Firebase Auth Here!
    private val auth = FirebaseAuth.getInstance()

    // Email and Password Authentication
    suspend fun signInWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            // TODO : implement when user login using email and password here !
            val resultAuth = auth.signInWithEmailAndPassword(email, password).await()

            Result.success(resultAuth.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Email and Password Registration
    suspend fun registerWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            // TODO : implement when user register using email and password here !
            val resultAuth = auth.createUserWithEmailAndPassword(email, password).await()

            Result.success(resultAuth.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO : implement check logged user here!
    // Check if user is currently logged in
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    // TODO : implement get current user id here!
    // Get current user ID
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    // Logout
    fun logout() {
        // TODO : implement logout here!
        auth.signOut()
    }
}