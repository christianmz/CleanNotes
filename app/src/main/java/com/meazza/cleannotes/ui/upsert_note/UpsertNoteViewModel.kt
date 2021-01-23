package com.meazza.cleannotes.ui.upsert_note

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.business.repository.NotesRepository
import com.meazza.cleannotes.util.Constants.KEY_LOGGED_IN_EMAIL
import kotlinx.coroutines.launch
import java.util.*

class UpsertNoteViewModel @ViewModelInject constructor(
    private val notesRepository: NotesRepository,
    private val prefs: SharedPreferences
) : ViewModel() {

    val noteArgs = MutableLiveData<Note>()
    val noteTitle = MutableLiveData<String>()
    val noteContent = MutableLiveData<String>()

    init {
        noteArgs.value?.let {
            noteTitle.value = it.title
            noteContent.value = it.content
        }
    }

    fun saveNote() {

        val email = prefs.getString(KEY_LOGGED_IN_EMAIL, "")
        val title = noteTitle.value
        val content = noteContent.value
        val date = System.currentTimeMillis()
        val owners = noteArgs.value?.owners ?: listOf(email)
        val id = noteArgs.value?.id ?: UUID.randomUUID().toString()

        if (!title.isNullOrEmpty() && !content.isNullOrEmpty()) {

            val note = Note(title, content, date, owners, id = id)

            viewModelScope.launch {
                notesRepository.saveNote(note)
            }
        }
    }

    fun shareNote(owner: String) {
        noteArgs.value?.let { note ->
            viewModelScope.launch {
                notesRepository.shareNote(owner, note.id)
            }
        }
    }

    fun deleteNote() {
        noteArgs.value?.let { note ->
            viewModelScope.launch {
                notesRepository.deleteNote(note.id)
            }
        }
    }
}