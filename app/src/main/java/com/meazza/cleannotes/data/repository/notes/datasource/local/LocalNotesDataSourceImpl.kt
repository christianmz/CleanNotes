package com.meazza.cleannotes.data.repository.notes.datasource.local

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.data.db.dao.DeletedNoteIdDao
import com.meazza.cleannotes.data.db.dao.NoteDao
import com.meazza.cleannotes.data.db.entity.DeletedNoteIdEntity
import com.meazza.cleannotes.data.db.entity.NoteEntity
import com.meazza.cleannotes.data.util.toNoteList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalNotesDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val deletedNoteIdDao: DeletedNoteIdDao
) : LocalNotesDataSource {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { it.toNoteList() }
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(NoteEntity.fromNote(note))
    }

    override suspend fun deleteNote(id: String) {
        noteDao.deleteNoteById(id)
    }

    override suspend fun deleteAllNote() {
        noteDao.deleteAllNotes()
    }

    override suspend fun saveDeletedNote(id: String) {
        deletedNoteIdDao.insertDeletedNoteId(DeletedNoteIdEntity(id))
    }

    override suspend fun getAllUnsyncedNotes(): List<Note> {
        return noteDao.getAllUnsyncedNotes().toNoteList()
    }

    override suspend fun getAllDeletedNotes(): List<DeletedNoteIdEntity> {
        return deletedNoteIdDao.getAllDeletedNoteIds()
    }

    override suspend fun deleteDeletedNote(id: String) {
        deletedNoteIdDao.deleteDeletedNoteID(id)
    }
}