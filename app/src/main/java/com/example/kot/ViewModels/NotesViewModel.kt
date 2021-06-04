package com.example.kot.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot.Models.Note
import androidx.lifecycle.asLiveData
import com.example.kot.Store.Repository
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class NotesViewModel(private val noteRepository: Repository):ViewModel() {
    val allNotes: LiveData<List<Note>> = noteRepository.getAllNotes().asLiveData()
}

