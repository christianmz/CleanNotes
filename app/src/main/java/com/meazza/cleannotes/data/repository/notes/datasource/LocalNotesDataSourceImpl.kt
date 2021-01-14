package com.meazza.cleannotes.data.repository.notes.datasource

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.data.db.dao.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalNotesDataSourceImpl @Inject constructor(
    private val dao: NoteDao
) : LocalNotesDataSource {

    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes().map { it.map { note -> note.toNote() } }
    }
}