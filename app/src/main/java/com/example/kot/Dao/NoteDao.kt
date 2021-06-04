package com.example.kot.Dao

import androidx.constraintlayout.helper.widget.Flow
import androidx.room.*
import com.example.kot.Models.Note
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long
    @Update
    suspend fun updateNote(note: Note)
    @Delete
    suspend fun deleteNote(note: Note)
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<Note>>
}