package sg.motion.tutorialnetworking.data.mapper

import sg.motion.tutorialnetworking.data.data_source.remote.dto.NoteRequestDto
import sg.motion.tutorialnetworking.data.data_source.remote.dto.NoteResponseDto
import sg.motion.tutorialnetworking.data.model.Note

fun NoteResponseDto.toModel() = Note(
    // TODO : update this mapper from NoteResponseDto to Note
    id = this.id,
    authorName = this.authorName,
    content = this.content,
    createdAt = this.updatedAt,
    updatedAt = this.updatedAt
)

fun Note.toRequestDto() = NoteRequestDto(
    // TODO : update this mapper from Note to NoteRequestDto
    authorName = this.authorName,
    content = this.content,
    createdAt = this.updatedAt,
    updatedAt = this.updatedAt
)