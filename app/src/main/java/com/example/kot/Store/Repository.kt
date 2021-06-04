package com.example.kot.Store

import com.example.kot.Dao.NoteDao
import com.example.kot.Models.Note

class Repository(private val noteDao: NoteDao) {
    fun getAllNotes() = noteDao.getAllNotes()
    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}