package sg.motion.tutorialfirebase.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import sg.motion.tutorialfirebase.core.routes.AppRoutes
import sg.motion.tutorialfirebase.data.repository.AuthRepository

// Splash Screen
@Composable
fun SplashScreen(
    navController: NavController,
    authRepository: AuthRepository
) {
    // Simple logic to determine initial navigation
    LaunchedEffect(Unit) {
        // Simulate some startup logic
        delay(1500) // Optional delay to show splash screen

        if (authRepository.isUserLoggedIn()) {
            // Navigate to home if already logged in
            navController.navigate(AppRoutes.Home.route) {
                // Clear the back stack
                popUpTo(AppRoutes.Splash.route) { inclusive = true }
            }
        } else {
            // Navigate to login
            navController.navigate(AppRoutes.Login.route) {
                popUpTo(AppRoutes.Splash.route) { inclusive = true }
            }
        }
    }

    // Splash screen UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your App Name",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}