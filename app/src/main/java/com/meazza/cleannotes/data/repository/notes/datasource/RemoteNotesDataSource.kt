package com.meazza.cleannotes.data.repository.notes.datasource

import com.meazza.cleannotes.business.domain.Note

interface RemoteNotesDataSource {
    suspend fun getAllNotes(): List<Note>?
}