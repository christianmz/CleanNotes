package com.meazza.cleannotes.business.repository

import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.vo.Resource
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<Resource<List<Note>>>
}