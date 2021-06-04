package com.example.kot.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot.Models.Note
import com.example.kot.Store.Repository
import kotlinx.coroutines.launch

class ReadNoteViewModel(private val noteRepository: Repository):ViewModel() {
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
}