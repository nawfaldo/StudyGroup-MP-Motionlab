package sg.motion.tutorialfirebase.data.repository

import android.content.Context

class AuthRepository(private val context: Context) {
    // TODO : init Firebase Auth Here!

    // Email and Password Authentication
    suspend fun signInWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            // TODO : implement when user login using email and password here !
            Result.success("loggedin")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Email and Password Registration
    suspend fun registerWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            // TODO : implement when user register using email and password here !
            Result.success("registered")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO : implement check logged user here!
    // Check if user is currently logged in
    fun isUserLoggedIn(): Boolean = false

    // TODO : implement get current user id here!
    // Get current user ID
    fun getCurrentUserId(): String? = ""

    // Logout
    fun logout() {
        // TODO : implement logout here!
    }
}