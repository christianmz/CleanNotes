package com.meazza.cleannotes.data.util

import com.meazza.cleannotes.data.db.entity.NoteEntity
import com.meazza.cleannotes.data.network.dto.NoteDto

fun List<NoteEntity>.toNoteList() = map { it.toNote() }

fun List<NoteDto>.toListNote() = map { it.toNote() }
