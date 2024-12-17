package sg.motion.tutorialfirebase.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import sg.motion.tutorialfirebase.data.repository.NotesRepository
import sg.motion.tutorialfirebase.ui.home.widgets.NoteItem
import java.util.Date

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
    val notesRepository = remember { NotesRepository(userId) }

    // Coroutine scope for async operations
    val scope = rememberCoroutineScope()

    // Collect notes
    LaunchedEffect(Unit) {
        // Collect Notes One time
        notesRepository.getNotes().collect { result ->
            result.onSuccess {
                notes = it
            }.onFailure {
                errorMessage = it.localizedMessage ?: "Failed to load notes"
            }
        }

        // Collect Notes Real time
//        notesRepository.getNotesRealTime().collect { result ->
//            result.onSuccess { updatedNotes ->
//                // Notes will automatically update in real-time
//                notes = updatedNotes
//            }.onFailure {
//                errorMessage = it.localizedMessage ?: "Failed to load notes"
//            }
//        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                actions = {
                    IconButton(
                        onClick = {
                            // Logout logic
                            authRepository.logout()
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
                            scope.launch {
                                val newNote = Note(
                                    content = newNoteContent,
                                    userId = userId,
                                    createdAt = Date()
                                )

                                val result = notesRepository.createNote(newNote)
                                result.onFailure {
                                    errorMessage = it.localizedMessage ?: "Failed to create note"
                                }

                                // Clear input after creating note
                                newNoteContent = ""
                            }
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
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onDelete = {
                            scope.launch {
                                val result = notesRepository.deleteNote(note.id)
                                result.onFailure {
                                    errorMessage = it.localizedMessage ?: "Failed to delete note"
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}