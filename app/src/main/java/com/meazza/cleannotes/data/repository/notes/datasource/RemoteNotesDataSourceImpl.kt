package com.meazza.cleannotes.data.repository.notes.datasource

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.data.network.dto.NoteDto
import com.meazza.cleannotes.data.network.request.DeleteNoteRequest
import com.meazza.cleannotes.data.network.service.KtorNotesService
import javax.inject.Inject

class RemoteNotesDataSourceImpl @Inject constructor(
    private val service: KtorNotesService
) : RemoteNotesDataSource {

    override suspend fun getAllNotes(): List<Note>? {
        return service.getNotes().body()?.let {
            it.map { noteDto ->
                noteDto.toNote()
            }
        }
    }

    override suspend fun addNote(note: Note): Boolean {
        return service.addNote(NoteDto.fromNote(note)).isSuccessful
    }

    override suspend fun deleteNote(id: String): Boolean {
        return service.deleteNote(DeleteNoteRequest(id)).isSuccessful
    }
}