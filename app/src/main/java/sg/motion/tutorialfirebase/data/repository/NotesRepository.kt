package sg.motion.tutorialfirebase.data.repository

import android.util.Log
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
    // TODO : init Firestore Database Here!
    val firestore = FirebaseFirestore.getInstance()
    // TODO : For easy to access init collection here !
    val notesRef = firestore.collection("notes")

    // Create a new note
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

    // Get all notes for the current user
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

    // Delete a note
    suspend fun deleteNote(noteId: String): Result<Unit> {
        return try {
            // TODO : implement delete notes here!
            notesRef.document(noteId).delete().await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Update an existing note
    suspend fun updateNote(note: Note): Result<Unit> {
        return try {
            // TODO : implement update notes here!
            notesRef.document(note.id).set(note).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
