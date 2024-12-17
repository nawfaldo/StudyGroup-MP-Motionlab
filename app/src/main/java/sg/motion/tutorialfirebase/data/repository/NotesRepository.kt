package sg.motion.tutorialfirebase.data.repository

import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import sg.motion.tutorialfirebase.data.model.Note

class NotesRepository(private val userId: String) {
    private val firestore = FirebaseFirestore.getInstance()
    private val notesCollection = firestore.collection("notes")

    // Create a new note
    suspend fun createNote(note: Note): Result<String> {
        return try {
            // Ensure the note is associated with the current user
            val noteWithUserId = note.copy(userId = userId)
            val docRef = notesCollection.add(noteWithUserId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get all notes for the current user
    fun getNotes(): Flow<Result<List<Note>>> = flow {
        try {
            val snapshot = notesCollection
                .whereEqualTo("userId", userId)
                .orderBy("createdAt")
                .get()
                .await()

            val notes = snapshot.documents.mapNotNull { doc ->
                doc.toObject<Note>()?.copy(id = doc.id)
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
        val listenerRegistration: ListenerRegistration = notesCollection
            .whereEqualTo("userId", userId)
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, e ->
                // Handle any errors
                if (e != null) {
                    Log.e("NotesRepository", "getNotesRealTime: "+ e.localizedMessage)
                    trySend(Result.failure(e))
                    return@addSnapshotListener
                }

                // Process the snapshot
                if (snapshot != null) {
                    val notes = snapshot.documents.mapNotNull { doc ->
                        doc.toObject<Note>()?.copy(id = doc.id)
                    }
                    trySend(Result.success(notes))
                }
            }

        // Ensure the listener is properly closed when the flow is cancelled
        awaitClose {
            listenerRegistration.remove()
        }
    }

    // Delete a note
    suspend fun deleteNote(noteId: String): Result<Unit> {
        return try {
            notesCollection.document(noteId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Update an existing note
    suspend fun updateNote(note: Note): Result<Unit> {
        return try {
            notesCollection.document(note.id).set(note).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
