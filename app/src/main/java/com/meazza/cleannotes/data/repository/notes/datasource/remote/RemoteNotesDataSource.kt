package com.meazza.cleannotes.data.repository.notes.datasource.remote

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.vo.Resource

interface RemoteNotesDataSource {
    suspend fun getAllNotes(): List<Note>?
    suspend fun addNote(note: Note): Boolean
    suspend fun deleteNote(id: String): Boolean
    suspend fun shareNote(owner: String, noteId: String): Resource<String>
}