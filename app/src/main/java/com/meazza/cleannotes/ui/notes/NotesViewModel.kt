package com.meazza.cleannotes.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meazza.cleannotes.ui.notes.adapter.NoteAdapter

class NotesViewModel : ViewModel() {

    val isEmptyList = MutableLiveData<Boolean>()

    val adapter = NoteAdapter

}