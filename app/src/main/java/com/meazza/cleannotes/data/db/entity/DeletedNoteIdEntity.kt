package com.meazza.cleannotes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meazza.cleannotes.util.Constants.TABLE_NAME_DELETED_NOTES

@Entity(tableName = TABLE_NAME_DELETED_NOTES)
data class DeletedNoteIdEntity(
    @PrimaryKey(autoGenerate = false)
    val deletedNoteId: String
)