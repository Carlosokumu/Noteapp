package com.example.kot.di

import com.example.kot.Store.Repository
import org.koin.dsl.module

val repositoryModule = module(
) {
    single { Repository(get()) }
}
