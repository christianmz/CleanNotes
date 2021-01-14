package com.meazza.cleannotes.data.repository.notes

import android.content.Context
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.repository.NotesRepository
import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.data.repository.notes.datasource.LocalNotesDataSource
import com.meazza.cleannotes.data.repository.notes.datasource.RemoteNotesDataSource
import com.meazza.cleannotes.util.checkForInternetConnection
import com.meazza.cleannotes.vo.networkBoundResource
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val localNotesDataSource: LocalNotesDataSource,
    private val remoteNotesDataSource: RemoteNotesDataSource,
    private val context: Context
) : NotesRepository {

    override fun getAllNotes(): Flow<Resource<List<Note>>> {
        return networkBoundResource(
            query = {
                localNotesDataSource.getAllNotes()
            },
            fetch = {
                remoteNotesDataSource.getAllNotes()
            },
            saveFetchResult = {

            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }


}