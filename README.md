LOGIN
<img width="381" alt="Screenshot 2025-02-13 at 6 35 17 pm" src="https://github.com/user-attachments/assets/ee17362c-0804-4965-966f-37e9e60a8c45" />
diatas adalah ui untuk login
suspend fun signInWithEmailPassword(email: String, password: String): Result<String> {
    return try {
        // TODO : implement when user login using email and password here !
        val resultAuth = auth.signInWithEmailAndPassword(email, password).await()

        Result.success(resultAuth.user?.uid ?: "")
    } catch (e: Exception) {
        Result.failure(e)
    }
}
diatas adalah kode backend untuk login menggunakan email dan password
Button(
    onClick = {
        errorMessage = ""
        scope.launch {
            // TODO : call sign in with email from repository here!
            val result = authRepository.signInWithEmailPassword(email, password)
            result.onSuccess {
                navController.navigate(AppRoutes.Home.route) {
                    popUpTo(AppRoutes.Login.route) {inclusive = true}
                }
            }.onFailure { e ->
                errorMessage = e.localizedMessage ?: "Login Failed"
            }
        }
    },
    modifier = Modifier.fillMaxWidth()
) {
    Text("Login")
}
diatas adalah kode frontend untuk login menggunakan email dan password
<img width="352" alt="Screenshot 2025-02-13 at 6 35 49 pm" src="https://github.com/user-attachments/assets/4000cb8f-e043-45c1-a232-1ebe4dd62c08" />
diatas adalah ui login ketika user salah input

REGISTER
<img width="350" alt="Screenshot 2025-02-13 at 6 35 23 pm" src="https://github.com/user-attachments/assets/7201f90d-c7de-468e-a3b2-9ed0e5344f10" />
diatas adalah ui untuk register
suspend fun registerWithEmailPassword(email: String, password: String): Result<String> {
    return try {
        // TODO : implement when user register using email and password here !
        val resultAuth = auth.createUserWithEmailAndPassword(email, password).await()

        Result.success(resultAuth.user?.uid ?: "")
    } catch (e: Exception) {
        Result.failure(e)
    }
}
diatas adalah kode backend untuk register menggunakan email dan password
scope.launch {
    // TODO : call register with email from repository here!
    val result = authRepository.registerWithEmailPassword(email, password)
    result.onSuccess {
        navController.navigate(AppRoutes.Home.route) {
            popUpTo(AppRoutes.Login.route) { inclusive = true }
        }
    }.onFailure { e ->
        errorMessage = e.localizedMessage ?: "Register Failed"
    }
}
diatas adalah kode frontend untuk register

LOGOUT
<img width="321" alt="Screenshot 2025-02-13 at 6 35 01 pm" src="https://github.com/user-attachments/assets/e1cac1c4-cd03-4608-8892-11c065ca5b61" />
diatas adalah ui untuk logout dan profile screen
fun logout() {
    // TODO : implement logout here!
    auth.signOut()
}
diatas adalah kode backend untuk logout
Button(
    onClick = {
        // Logout logic
        // TODO : do logout here!
        authRepository.logout()

        // Navigate back to login
        navController.navigate(AppRoutes.Login.route) {
            popUpTo(AppRoutes.Home.route) { inclusive = true }
        }
    }
) {
    Text("Logout")
}
diatas adalah kode frontend untuk logout

LIHAT SEMUA NOTES
<img width="339" alt="Screenshot 2025-02-13 at 6 34 53 pm" src="https://github.com/user-attachments/assets/3007925f-e6e7-4b9f-828c-a680a7f0a07a" />
diatas adalah ui untuk home screen
    fun getNotes(): Flow<Result<List<Note>>> = flow {
        try {
            // TODO : get list of notes here!
            val snapshot = notesRef
                .whereEqualTo("userId", userId)
                .orderBy("createdAt")
                .get().await()

        // TODO : convert Documents to List of object Here !
        val notes = snapshot.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject<Note>()?.copy(id = documentSnapshot.id)
        }

        emit(Result.success(notes))
    } catch (e: Exception) {
        Log.e("NotesRepository", "getNotes: "+ e.localizedMessage)
        emit(Result.failure(e))
    }
}

// Real-time notes listener with Flow
fun getNotesRealTime(): Flow<Result<List<Note>>> = callbackFlow {
    // Create a listener for real-time updates
    // TODO : create listener to listen firestore realtime collections here!
    val listenerRegistration: ListenerRegistration = notesRef
        .whereEqualTo("userId", userId)
        .orderBy("createdAt")
        .addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("NotesRepository", "getNotesRealTime: " + e.localizedMessage)
                trySend(Result.failure(e))
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val notes = snapshot.documents.mapNotNull {
                    it.toObject<Note>()?.copy(id = it.id)
                }
                trySend(Result.success(notes))
            }
        }
    // Ensure the listener is properly closed when the flow is cancelled
    awaitClose {
        // TODO : don't forget to remove listener here!
        listenerRegistration.remove()
    }
}
diatas adalah kode backend untuk melihat semua notes
ada yang realtime atau auto update ketika user menambah atau menhapus notes
dan ada yang ketika user baru membuka aplikasi

MENAMBAH NOTES
<img width="367" alt="Screenshot 2025-02-13 at 6 35 10 pm" src="https://github.com/user-attachments/assets/6e1ca516-171b-4a9a-bf26-4f8d35b32961" />
suspend fun createNote(note: Note): Result<String> {
    return try {
        // Ensure the note is associated with the current user
        // TODO : add new Document to Firestore Here!
        val newNote = note.copy(userId = userId)
        val resultNote = notesRef.add(note).await()

        Result.success(resultNote.id)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
diatas adalah kode backend untuk menambahkan notes
IconButton(
    modifier = Modifier.weight(1f).size(24.dp),
    onClick = {
        // Create new note
        // TODO : call create note repository here if note is not empty !
        scope.launch {
            if (newNoteContent.isNotBlank()) {
                if (newNoteContent.isNotBlank()) {
                    val newNote = Note(
                        content = newNoteContent,
                        userId = userId,
                        createdAt = Date()
                    )

                    val result = notesRepository.createNote(newNote)
                    result.onFailure {
                        errorMessage = it.localizedMessage ?: "Failed to create note"
                    }

                    newNoteContent = ""
                }
            }
        }
    }
) {
    Icon(Icons.Default.Send, contentDescription = "Add Note")
}
diatas adalah kode frontend untuk menambahkan notes

MENGHAPUS NOTE
suspend fun deleteNote(noteId: String): Result<Unit> {
    return try {
        // TODO : implement delete notes here!
        notesRef.document(noteId).delete().await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
diatas adalah kode backend untuk menghapus notes
LazyColumn {
    // Load all notes
    items(notes) { note ->
        NoteItem(
            note = note,
            onDelete = {
                scope.launch {
                    // TODO :  call delete note from repository here!
                    val result = notesRepository.deleteNote(note.id)
                    result.onFailure {
                        errorMessage = it.localizedMessage ?: "Failed to delete note"
                    }
                }
            }
        )
    }
}
kode diatas adalah cara memanggil note widget di home screen dan logik untuk menghapus note di frontend  
