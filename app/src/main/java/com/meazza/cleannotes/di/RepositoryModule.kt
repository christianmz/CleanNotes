package com.meazza.cleannotes.di

import com.meazza.cleannotes.business.repository.AuthRepository
import com.meazza.cleannotes.business.repository.NotesRepository
import com.meazza.cleannotes.data.db.dao.DeletedNoteIdDao
import com.meazza.cleannotes.data.db.dao.NoteDao
import com.meazza.cleannotes.data.network.service.KtorNotesAuthService
import com.meazza.cleannotes.data.network.service.KtorNotesService
import com.meazza.cleannotes.data.repository.auth.AuthRepositoryImpl
import com.meazza.cleannotes.data.repository.auth.datasource.AuthService
import com.meazza.cleannotes.data.repository.auth.datasource.AuthServiceImpl
import com.meazza.cleannotes.data.repository.notes.NotesRepositoryImpl
import com.meazza.cleannotes.data.repository.notes.datasource.local.LocalNotesDataSource
import com.meazza.cleannotes.data.repository.notes.datasource.local.LocalNotesDataSourceImpl
import com.meazza.cleannotes.data.repository.notes.datasource.remote.RemoteNotesDataSource
import com.meazza.cleannotes.data.repository.notes.datasource.remote.RemoteNotesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun providesAuthService(ktorService: KtorNotesAuthService): AuthService {
        return AuthServiceImpl(ktorService)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesLocalNotesDataSource(
        noteDao: NoteDao,
        deletedNoteIdDao: DeletedNoteIdDao
    ): LocalNotesDataSource {
        return LocalNotesDataSourceImpl(noteDao, deletedNoteIdDao)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesRemoteNotesDataSource(ktorService: KtorNotesService): RemoteNotesDataSource {
        return RemoteNotesDataSourceImpl(ktorService)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @ActivityRetainedScoped
    @Provides
    fun providesNotesRepository(
        localDataSource: LocalNotesDataSource,
        remoteDataSource: RemoteNotesDataSource
    ): NotesRepository {
        return NotesRepositoryImpl(localDataSource, remoteDataSource)
    }
}