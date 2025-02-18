<img width="387" alt="Screenshot 2025-02-18 at 7 31 17 am" src="https://github.com/user-attachments/assets/d349cff0-8dfb-4457-8dd1-e6e8d6971553" />
<img width="412" alt="Screenshot 2025-02-18 at 7 31 01 am" src="https://github.com/user-attachments/assets/55ef6c54-8c9f-4540-93c8-ed4feffafeb4" />
<img width="387" alt="Screenshot 2025-02-18 at 7 30 49 am" src="https://github.com/user-attachments/assets/0c5312eb-34aa-402e-b0a3-9560028157e9" />

GET ALL NOTES

@GET("/api/notes")
suspend fun getAllNotes() : List<NoteResponseDto>

diatas adalah kode untuk mengambil notes dari internet

suspend fun getAllNotes() : Result<List<Note>> {
    return try {
        val result = service.getAllNotes().map { item -> item.toModel() }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

diatas adalah kode untuk mengambil notes di frontend

CREATE NOTE

@POST("/api/notes/")
suspend fun postNote(
    @Body request : NoteRequestDto
) : NoteResponseDto

diatas adalah kode untuk memposting note ke internet

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

diatas adalah kode untuk membuat note di frontend

EDIT NOTE

@PUT("api/notes/{noteId}")
suspend fun putNote(
    @Path("noteId") noteId: String,
    @Body request: NoteRequestDto
) : NoteResponseDto

diatas adalah kode untuk menedit note ke internet

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

diatas adalah kode untuk menedit note di frontend

DELETE NOTE

@DELETE("api/notes/{noteId}")
suspend fun deleteNote(
    @Path("noteId") noteId: String
) : NoteDeleteResponseDto

diatas adalah kode untuk delete note ke internet

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

diatas adalah kode untuk delete note di frontend
