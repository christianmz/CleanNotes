package com.meazza.cleannotes.ui.upsert_note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpsertNoteViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun saveNote() {

    }
}