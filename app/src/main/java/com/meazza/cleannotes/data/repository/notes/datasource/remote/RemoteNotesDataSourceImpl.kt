package com.meazza.cleannotes.data.repository.notes.datasource.remote

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.data.network.dto.NoteDto
import com.meazza.cleannotes.data.network.request.AddOwnerRequest
import com.meazza.cleannotes.data.network.request.DeleteNoteRequest
import com.meazza.cleannotes.data.network.service.KtorNotesService
import com.meazza.cleannotes.data.util.toListNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RemoteNotesDataSourceImpl @Inject constructor(
    private val service: KtorNotesService
) : RemoteNotesDataSource {

    override suspend fun getAllNotes(): List<Note>? {
        val code = service.getNotes()
        Timber.d("${code.code()}")
        return service.getNotes().body()?.toListNote()
    }

    override suspend fun addNote(note: Note): Boolean {
        val code = service.addNote(NoteDto.fromNote(note))
        val r = service.addNote(NoteDto.fromNote(note)).isSuccessful
        Timber.d("${code.code()}")
        return r
    }

    override suspend fun deleteNote(id: String): Boolean {
        return service.deleteNote(DeleteNoteRequest(id)).isSuccessful
    }

    override suspend fun shareNote(owner: String, noteId: String): Resource<String> {
        return withContext(Dispatchers.IO) {

            try {

                val response = service.addOwnerToNote(AddOwnerRequest(owner, noteId))

                if (response.isSuccessful && response.body()!!.isSuccessful) {
                    Resource.success(response.body()?.message)
                } else {
                    Resource.error(response.body()?.message ?: response.message(), null)
                }

            } catch (e: Exception) {
                Resource.error(
                    "Couldn't connect to the servers. Check your internet connection",
                    null
                )
            }
        }
    }
}
