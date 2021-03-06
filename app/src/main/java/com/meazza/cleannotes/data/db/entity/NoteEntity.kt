package com.meazza.cleannotes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meazza.cleannotes.business.domain.Note
import com.meazza.cleannotes.data.util.Constants.TABLE_NAME_NOTES
import java.util.*

@Entity(tableName = TABLE_NAME_NOTES)
data class NoteEntity(
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String?>,
    var isSynced: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString()
) {
    companion object {
        fun fromNote(note: Note): NoteEntity {
            return NoteEntity(
                note.title,
                note.content,
                note.date,
                note.owners,
                note.isSynced,
                note.id
            )
        }
    }

    fun toNote() = Note(title, content, date, owners, isSynced, id)
}