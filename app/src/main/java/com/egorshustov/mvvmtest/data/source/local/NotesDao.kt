package com.egorshustov.mvvmtest.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.egorshustov.mvvmtest.data.Note

/**
 * Generally this is good approach to create one Dao per entity.
 */
@Dao
interface NotesDao {

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getNotes(): LiveData<List<Note>>
    // Now we can observe List<Note> object with LiveData.
    // As soon as any changes will appear in note_table,
    // List<Note> will automatically be updated by Room
    // and Activity will be notified about it.

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()
}