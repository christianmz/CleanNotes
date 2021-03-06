package com.meazza.cleannotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meazza.cleannotes.data.db.dao.DeletedNoteIdDao
import com.meazza.cleannotes.data.db.dao.NoteDao
import com.meazza.cleannotes.data.db.entity.DeletedNoteIdEntity
import com.meazza.cleannotes.data.db.entity.NoteEntity

@Database(
    entities = [NoteEntity::class, DeletedNoteIdEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NotesDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun deletedNoteIdDao(): DeletedNoteIdDao
}