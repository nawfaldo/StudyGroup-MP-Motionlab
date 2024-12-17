package sg.motion.tutorialfirebase.core.routes

sealed class AppRoutes(val route: String) {
    // Authentication Routes
    object Splash : AppRoutes("splash")
    object Login : AppRoutes("login")
    object Register : AppRoutes("register")

    // Main App Routes
    object Home : AppRoutes("home")
    object Profile : AppRoutes("profile")
    object Settings : AppRoutes("settings")
}