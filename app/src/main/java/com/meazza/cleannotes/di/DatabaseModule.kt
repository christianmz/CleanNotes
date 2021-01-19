package com.meazza.cleannotes.di

import android.content.Context
import androidx.room.Room
import com.meazza.cleannotes.data.db.NotesDb
import com.meazza.cleannotes.data.db.dao.DeletedNoteIdDao
import com.meazza.cleannotes.data.db.dao.NoteDao
import com.meazza.cleannotes.data.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object DatabaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDb {
        return Room.databaseBuilder(context, NotesDb::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @ActivityRetainedScoped
    @Provides
    fun provideNoteDao(db: NotesDb): NoteDao {
        return db.noteDao()
    }

    @ActivityRetainedScoped
    @Provides
    fun provideDeletedNoteIdDao(db: NotesDb): DeletedNoteIdDao {
        return db.deletedNoteIdDao()
    }
}