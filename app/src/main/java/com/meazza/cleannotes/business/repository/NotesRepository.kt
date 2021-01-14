package com.meazza.cleannotes.business.repository

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.vo.Resource
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getAllNotes(): Flow<Resource<List<Note>>>
    suspend fun saveNote(note: Note)
    suspend fun deleteNote(id: String)
}