package com.meazza.cleannotes.data.util

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.data.db.entity.NoteEntity

fun fromNote(note: Note): NoteEntity {
    return NoteEntity(
        note.title,
        note.content,
        note.date,
        note.owners,
        note.color,
        note.isSynced,
        note.id
    )
}

fun toNote(dto: NoteEntity): Note {
    return Note(dto.title, dto.content, dto.date, dto.owners, dto.color, dto.isSynced, dto.id)
}

fun toNoteList(initial: List<NoteEntity>): List<Note> {
    return initial.map { toNote(it) }
}

fun fromNotList(initial: List<Note>): List<NoteEntity> {
    return initial.map { fromNote(it) }
}