package com.example.tutorialroom.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.tutorialroom.data.local.Contact
import com.example.tutorialroom.data.local.ContactDatabase
import com.example.tutorialroom.data.repository.ContactRepository
import com.example.tutorialroom.ui.home.widget.AddContactDialog
import com.example.tutorialroom.ui.home.widget.ContactItem
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
) {
    var openDialog by remember { mutableStateOf(false) }
    var selectedContact by remember { mutableStateOf(Contact()) }
    var isEdit by remember { mutableStateOf(false) }

    // TODO: Remove this after implementing Room Database
    // Dummy 2 contacts

    val context = LocalContext.current

    val database = remember { ContactDatabase.getDatabase(context) }
    val contactRepository = remember { ContactRepository.getInstance(database.contactDao()) }

    val scope = rememberCoroutineScope()

    val contacts by contactRepository.getContacts().observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isEdit = false
                openDialog = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (openDialog) {
                AddContactDialog(
                    contact = selectedContact,
                    onDismiss = {
                        selectedContact = Contact()
                        openDialog = false
                    },
                    onSave = { contact ->
                        if (isEdit) {
                            // TODO: Replace this with Room Database implementation
                            scope.launch {
                                contactRepository.updateContact(contact)
                            }
                            openDialog = false
                        } else {
                            // TODO: Replace this with Room Database implementation
                            scope.launch {
                                contactRepository.insertContact(contact)
                            }
                            openDialog = false
                        }

                    }
                )
            }


            // Content
            if (contacts.isEmpty()) {
                Text(
                    text = "No contacts available",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    // Load all Contact
                    items(contacts) { contact ->
                        ContactItem(
                            contact = contact,
                            onUpdate = {
                                selectedContact = contact
                                openDialog = true
                                isEdit = true
                            },
                            onDelete = {
                                // TODO: Replace this with Room Database implementation
                                scope.launch {
                                    contactRepository.deleteContact(contact)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}