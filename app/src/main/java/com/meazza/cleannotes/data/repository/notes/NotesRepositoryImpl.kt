package com.meazza.cleannotes.data.repository.notes

import android.content.Context
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.repository.NotesRepository
import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.data.repository.networkBoundResource
import com.meazza.cleannotes.data.repository.notes.datasource.LocalNotesDataSource
import com.meazza.cleannotes.data.repository.notes.datasource.RemoteNotesDataSource
import com.meazza.cleannotes.util.checkForInternetConnection
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val localNotesDataSource: LocalNotesDataSource,
    private val remoteNotesDataSource: RemoteNotesDataSource,
    private val context: Context
) : NotesRepository {

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

    override suspend fun getAllNotes(): Flow<Resource<List<Note>>> {
        return networkBoundResource(
            query = {
                localNotesDataSource.getAllNotes()
            },
            fetch = {
                remoteNotesDataSource.getAllNotes()
            },
            saveFetchResult = { listNotes ->
                listNotes?.let { notes ->
                    notes.forEach { saveNote(it) }
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
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
}