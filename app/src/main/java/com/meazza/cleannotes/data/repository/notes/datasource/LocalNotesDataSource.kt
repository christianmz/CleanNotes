package com.meazza.cleannotes.data.repository.notes.datasource

import com.meazza.cleannotes.business.domain.Note
import kotlinx.coroutines.flow.Flow

interface LocalNotesDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(id: String)
    suspend fun saveDeletedNote(id: String)
    suspend fun deleteDeletedNote(id: String)
}