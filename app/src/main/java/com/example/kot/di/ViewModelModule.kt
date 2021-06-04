package com.example.kot.di

import com.example.kot.ViewModels.AddNoteViewModel
import com.example.kot.ViewModels.NotesViewModel
import com.example.kot.ViewModels.ReadNoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule  = module {
    viewModel { AddNoteViewModel(get()) }
    viewModel { NotesViewModel(get()) }
    viewModel { ReadNoteViewModel(get()) }

}