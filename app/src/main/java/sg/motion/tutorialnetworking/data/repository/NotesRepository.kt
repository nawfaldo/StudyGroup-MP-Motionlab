package sg.motion.tutorialnetworking.data.repository

import sg.motion.tutorialnetworking.data.data_source.remote.network.RetrofitInstance
import sg.motion.tutorialnetworking.data.mapper.toModel
import sg.motion.tutorialnetworking.data.mapper.toRequestDto
import sg.motion.tutorialnetworking.data.model.Note

class NotesRepository {
    // TODO : create global variable for api service here!
    private val service = RetrofitInstance.api

    // TODO : define getAllNotes from retrofit
    suspend fun getAllNotes() : Result<List<Note>> {
        return try {
            val result = service.getAllNotes().map { item -> item.toModel() }

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO : define getSelectedNote from retrofit
    suspend fun getSelectedNote(id: String) : Result<Note> {
        return try {
            val resultData = service.getNoteById(id)
            if (resultData.error != null) {
                Result.failure(Exception(resultData.error.message))
            } else {
                Result.success(resultData.toModel())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO : define createNote from retrofit
    suspend fun createNote(newNote: Note) : Result<Note> = try {
        val result = service.postNote(newNote.toRequestDto())
        if(result.error == null) {
            val resultNote = result.toModel()
            Result.success(resultNote)
        } else {
            Result.failure(Exception(result.error.message))
        }
    } catch (e : Exception) {
        Result.failure(e)
    }

    // TODO : define updateNoteWithPut from retrofit
    suspend fun updateNoteWithPut(newNote: Note) : Result<Note> = try {
        val result = service.putNote(newNote.id.orEmpty(), newNote.toRequestDto())
        if(result.error == null) {
            val resultNote = result.toModel()
            Result.success(resultNote)
        } else {
            Result.failure(Exception(result.error.message))
        }
    } catch (e : Exception) {
        Result.failure(e)
    }

    // TODO : define updateNoteWithPatch from retrofit
    suspend fun updateNoteWithPatch(newNote: Note) : Result<Note> = try {
        val result = service.patchNote(newNote.id.orEmpty(), newNote.toRequestDto())
        if(result.error == null) {
            val resultNote = result.toModel()
            Result.success(resultNote)
        } else {
            Result.failure(Exception(result.error.message))
        }
    } catch (e : Exception) {
        Result.failure(e)
    }

    // TODO : define deleteNote from retrofit
    suspend fun deleteNote(noteId: String) : Result<Boolean> = try {
        val result = service.deleteNote(noteId)

        if(result.success == true) {
            Result.success(true)
        } else {
            Result.failure(Exception(result.error?.message))
        }
    } catch (e : Exception) {
        Result.failure(e)
    }
}