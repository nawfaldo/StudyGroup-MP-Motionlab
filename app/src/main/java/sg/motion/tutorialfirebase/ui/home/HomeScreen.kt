package sg.motion.tutorialfirebase.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import sg.motion.tutorialfirebase.core.routes.AppRoutes
import sg.motion.tutorialfirebase.data.model.Note
import sg.motion.tutorialfirebase.data.repository.AuthRepository
import sg.motion.tutorialfirebase.ui.home.widgets.NoteItem

// Home Screen with Bottom Navigation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, authRepository: AuthRepository) {
    // State for notes and new note input
    var newNoteContent by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(listOf<Note>()) }
    var errorMessage by remember { mutableStateOf("") }

    // Get current user ID for notes repository
    val userId = authRepository.getCurrentUserId() ?: return
    // TODO : init notes repository here !

    // Coroutine scope for async operations
    val scope = rememberCoroutineScope()

    // Collect notes
    LaunchedEffect(Unit) {
        // IMPORTANT : Please select one of load type ( once / real time)

        // Collect Notes One time
        // TODO : load all notes once here!


        // Collect Notes Real time
        // TODO : load realtime notes here!

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                actions = {
                    IconButton(
                        onClick = {
                            // Logout logic
                            // TODO :  Do logout Here!

                            navController.navigate(AppRoutes.Login.route) {
                                popUpTo(AppRoutes.Home.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                // Home Navigation Item
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { /* Already on home screen */ }
                )

                // Profile Navigation Item
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = {
                        navController.navigate(AppRoutes.Profile.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // New Note Input
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newNoteContent,
                    onValueChange = { newNoteContent = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    placeholder = { Text("Write a new note...") },
                    maxLines = 3
                )
                IconButton(
                    modifier = Modifier.size(24.dp).padding(8.dp),
                    onClick = {
                        // Create new note
                        if (newNoteContent.isNotBlank()) {
                            // TODO : call create note repository here if note is not empty !
                        }
                    }
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Add Note")
                }

            }

            // Error Message
            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Notes List
            LazyColumn {
                // Load all notes
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onDelete = {
                            scope.launch {
                                // TODO :  call delete note from repository here!
                            }
                        }
                    )
                }
            }
        }
    }
}