package sg.motion.tutorialfirebase.ui.auth

import android.content.Context

// Authentication Handler (from previous example)
class AuthHandler(private val context: Context) {
    fun login(email: String, password: String): Boolean {
        // Basic validation
        return email.isNotBlank() && password.length >= 6
    }

    fun register(email: String, password: String): Boolean {
        // Basic registration validation
        return email.isNotBlank() &&
                password.length >= 6 &&
                email.contains("@")
    }

    fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPref = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", isLoggedIn)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        val sharedPref = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLoggedIn", false)
    }
}