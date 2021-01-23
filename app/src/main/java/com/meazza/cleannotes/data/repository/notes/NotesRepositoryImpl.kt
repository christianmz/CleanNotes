package com.meazza.cleannotes.data.repository.notes

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.repository.NotesRepository
import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.data.repository.notes.datasource.local.LocalNotesDataSource
import com.meazza.cleannotes.data.repository.notes.datasource.remote.RemoteNotesDataSource
import com.meazza.cleannotes.data.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val localNotesDataSource: LocalNotesDataSource,
    private val remoteNotesDataSource: RemoteNotesDataSource
) : NotesRepository {

    private var currentNotes: List<Note>? = null

    override suspend fun getAllNotes(isThereAnInternetConnection: Boolean): Flow<Resource<List<Note>>> {
        return networkBoundResource(
            query = {
                localNotesDataSource.getAllNotes()
            },
            fetch = {
                getSyncNotes()
                currentNotes
            },
            saveFetchResult = { listNotes ->
                listNotes?.let { notes ->
                    insertNotes(notes.onEach { it.isSynced = true })
                }
            },
            shouldFetch = {
                isThereAnInternetConnection
            }
        )
    }

    override suspend fun saveNote(note: Note) {

        val response = try {
            remoteNotesDataSource.addNote(note)
        } catch (e: Exception) {
            null
        }

        if (response != null && response) {
            localNotesDataSource.insertNote(note.apply { isSynced = true })
        } else {
            localNotesDataSource.insertNote(note)
        }
    }

    override suspend fun deleteNote(id: String) {

        val response = try {
            remoteNotesDataSource.deleteNote(id)
        } catch (e: Exception) {
            null
        }

        localNotesDataSource.deleteNote(id)

        if (response == null || !response) {
            localNotesDataSource.saveDeletedNote(id)
        } else {
            localNotesDataSource.deleteDeletedNote(id)
        }
    }

    override suspend fun shareNote(owner: String, noteId: String): Resource<String> {
        return remoteNotesDataSource.shareNote(owner, noteId)
    }

    private suspend fun insertNotes(notes: List<Note>) {
        notes.forEach { saveNote(it) }
    }

    private suspend fun getSyncNotes() {

        currentNotes = remoteNotesDataSource.getAllNotes()

        val deletedNoteIds = localNotesDataSource.getAllDeletedNotes()
        val unsyncedNotes = localNotesDataSource.getAllUnsyncedNotes()

        deletedNoteIds.forEach { id -> deleteNote(id.deletedNoteId) }
        unsyncedNotes.forEach { notes -> saveNote(notes) }

        currentNotes?.let { notes ->
            localNotesDataSource.deleteAllNote()
            insertNotes(notes.onEach { it.isSynced = true })
        }
    }
}