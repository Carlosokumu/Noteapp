package com.example.kot.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot.Models.Note
import com.example.kot.Store.Repository
import kotlinx.coroutines.launch

class AddNoteViewModel(private val noteRepository: Repository) : ViewModel() {
    fun saveNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }
}