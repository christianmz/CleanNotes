package com.meazza.cleannotes.data.repository.notes.datasource.local

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.data.db.entity.DeletedNoteIdEntity
import kotlinx.coroutines.flow.Flow

interface LocalNotesDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(id: String)
    suspend fun deleteAllNote()
    suspend fun saveDeletedNote(id: String)
    suspend fun getAllUnsyncedNotes(): List<Note>
    suspend fun getAllDeletedNotes(): List<DeletedNoteIdEntity>
    suspend fun deleteDeletedNote(id: String)
}