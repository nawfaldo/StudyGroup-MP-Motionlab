package sg.motion.tutorialfirebase.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sg.motion.tutorialfirebase.core.routes.AppRoutes
import sg.motion.tutorialfirebase.data.repository.AuthRepository
import sg.motion.tutorialfirebase.ui.home.HomeScreen
import sg.motion.tutorialfirebase.ui.login.LoginScreen
import sg.motion.tutorialfirebase.ui.profile.ProfileScreen
import sg.motion.tutorialfirebase.ui.register.RegisterScreen
import sg.motion.tutorialfirebase.ui.splash.SplashScreen

// Main Navigation Component
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val authRepository = remember { AuthRepository(context) }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Splash.route
    ) {
        // Splash Screen
        composable(AppRoutes.Splash.route) {
            SplashScreen(navController, authRepository)
        }

        // Login Screen
        composable(AppRoutes.Login.route) {
            LoginScreen(navController, authRepository)
        }

        // Register Screen
        composable(AppRoutes.Register.route) {
            RegisterScreen(navController, authRepository)
        }

        // Home Screen
        composable(AppRoutes.Home.route) {
            HomeScreen(navController, authRepository)
        }

        // Profile Screen
        composable(AppRoutes.Profile.route) {
            ProfileScreen(navController)
        }
    }
}