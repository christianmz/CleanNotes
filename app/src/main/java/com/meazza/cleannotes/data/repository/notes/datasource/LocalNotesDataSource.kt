package com.meazza.cleannotes.data.repository.notes.datasource

import com.meazza.cleannotes.business.domain.Note
import kotlinx.coroutines.flow.Flow

interface LocalNotesDataSource {
    fun getAllNotes(): Flow<List<Note>>
}