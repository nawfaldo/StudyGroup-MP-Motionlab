package com.example.tutorialroom.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.tutorialroom.data.model.Contact
import com.example.tutorialroom.ui.home.widget.AddContactDialog
import com.example.tutorialroom.ui.home.widget.ContactItem

@Composable
fun HomeScreen(
    navController: NavController,
) {
    var openDialog by remember { mutableStateOf(false) }
    var contacts by remember { mutableStateOf(listOf<Contact>()) }
    var selectedContact by remember { mutableStateOf(Contact()) }
    var isEdit by remember { mutableStateOf(false) }

    // TODO: Remove this after implementing Room Database
    // Dummy 2 contacts
    if (contacts.isEmpty()) {
        contacts = listOf(
            Contact(id = 1, name = "John Doe", number = "1234567890"),
            Contact(id = 2, name = "Jane Doe", number = "0987654321")
        )
    }

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
                            contacts = contacts.map {
                                if (it.id == contact.id) {
                                    contact
                                } else {
                                    it
                                }
                            }
                            openDialog = false
                        } else {
                            // TODO: Replace this with Room Database implementation
                            contacts = contacts.plus(contact)
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
                                contacts = contacts.minus(it)
                            }
                        )
                    }
                }
            }
        }
    }
}