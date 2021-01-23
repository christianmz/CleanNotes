package com.meazza.cleannotes.ui.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.repository.NotesRepository
import com.meazza.cleannotes.business.vo.Resource
import com.meazza.cleannotes.ui.notes.adapter.NoteAdapter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

class NotesViewModel @ViewModelInject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _forceUpdate = MutableLiveData(false)

    val adapter = NoteAdapter
    val isEmptyList = MutableLiveData<Boolean>()

    fun syncAllNotes() = _forceUpdate.postValue(true)

    fun getAllNotes(isThereAnInternetConnection: Boolean) = liveData {

        val notesChannel = Channel<Resource<List<Note>>>()

        notesRepository.getAllNotes(isThereAnInternetConnection)
            .collect {
                notesChannel.send(it)
            }

        emit(notesChannel.receiveAsFlow())
    }
}