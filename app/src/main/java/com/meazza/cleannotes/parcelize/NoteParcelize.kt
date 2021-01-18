package com.meazza.cleannotes.parcelize

import android.os.Parcelable
import com.meazza.cleannotes.business.domain.Note
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteParcelize(
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String>,
    var isSynced: Boolean,
    val id: String
) : Parcelable {

    companion object {
        fun fromNote(note: Note): NoteParcelize {
            return NoteParcelize(
                note.title,
                note.content,
                note.date,
                note.owners,
                note.isSynced,
                note.id
            )
        }
    }

    fun toNote(): Note {
        return Note(title, content, date, owners, isSynced, id)
    }
}
