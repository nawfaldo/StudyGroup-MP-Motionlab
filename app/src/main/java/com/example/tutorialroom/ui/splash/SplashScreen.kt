package com.example.tutorialroom.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tutorialroom.core.routes.AppRoutes
import kotlinx.coroutines.delay

// Splash Screen
@Composable
fun SplashScreen(
    navController: NavController,
) {
    // Simple logic to determine initial navigation
    LaunchedEffect(Unit) {
        // Simulate some startup logic
        delay(1500) // Optional delay to show splash screen

        // Navigate to home
        navController.navigate(AppRoutes.Home.route) {
            popUpTo(AppRoutes.Splash.route) { inclusive = true }
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