package com.meazza.cleannotes.data.network.dto

import com.google.gson.annotations.Expose
import com.meazza.cleannotes.business.domain.Note

data class NoteDto(
    val title: String,
    val content: String,
    val date: Long,
    val color: String,
    @Expose(deserialize = false, serialize = false)
    val isSynced: Boolean = false,
    val id: String
) {

    companion object {
        fun fromNote(note: Note): NoteDto {
            return NoteDto(
                note.title,
                note.content,
                note.date,
                note.color,
                note.isSynced,
                note.id
            )
        }
    }

    fun toNote(): Note {
        return Note(title, content, date, color, isSynced, id)
    }
}
