package com.meazza.cleannotes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.meazza.cleannotes.data.db.entity.DeletedNoteIdEntity

@Dao
interface DeletedNoteIdDao {

    @Query("SELECT * FROM deleted_notes_id")
    suspend fun getAllDeletedNoteIds(): List<DeletedNoteIdEntity>

    @Query("DELETE FROM deleted_notes_id WHERE deletedNoteId = :deletedNoteId")
    suspend fun deleteDeletedNoteID(deletedNoteId: String)

    @Insert(onConflict = REPLACE)
    suspend fun insertDeletedNoteId(deletedNoteId: DeletedNoteIdEntity)
}