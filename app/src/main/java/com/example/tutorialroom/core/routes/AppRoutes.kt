package com.example.tutorialroom.core.routes

sealed class AppRoutes(val route: String) {
    data object Splash : AppRoutes("splash")
    data object Home : AppRoutes("home")
}