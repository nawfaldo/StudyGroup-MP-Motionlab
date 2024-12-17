package sg.motion.tutorialfirebase.data.repository

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import sg.motion.tutorialfirebase.data.model.Note

class NotesRepository(private val userId: String) {
    // TODO : init Firestore Here!
    // TODO : For easy to access init collection here !

    // Create a new note
    suspend fun createNote(note: Note): Result<String> {
        return try {
            // Ensure the note is associated with the current user
            // TODO : add new Document to Firestore Here!

            Result.success("success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get all notes for the current user
    fun getNotes(): Flow<Result<List<Note>>> = flow {
        try {
            // TODO : get list of notes here!

            // TODO : convert Documents to List of object Here !
            val notes = listOf<Note>()

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

        // Ensure the listener is properly closed when the flow is cancelled
        awaitClose {
            // TODO : don't forget to remove listener here!
        }
    }

    // Delete a note
    suspend fun deleteNote(): Result<Unit> {
        return try {
            // TODO : implement delete notes here!
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Update an existing note
    suspend fun updateNote(): Result<Unit> {
        return try {
            // TODO : implement update notes here!
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
