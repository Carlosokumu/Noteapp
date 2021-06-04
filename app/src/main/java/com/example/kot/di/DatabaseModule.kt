package com.example.kot.di

import androidx.room.Room
import com.example.kot.Database.NoteDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), NoteDatabase::class.java, "carl")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<NoteDatabase>().noteDao() }
}