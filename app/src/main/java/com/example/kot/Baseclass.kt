package com.example.kot

import android.app.Application
import com.example.kot.Objects.ObjectBox
import com.example.kot.di.databaseModule
import com.example.kot.di.repositoryModule
import com.example.kot.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Baseclass:Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    //initialize Koin
    private fun initKoin() {
        startKoin {
            AndroidLogger()
            androidContext(this@Baseclass)
            modules(listOf(databaseModule, viewModelModule, repositoryModule))
        }
        ObjectBox.init(this)
    }
    private fun initTimber() {
            Timber.plant(Timber.DebugTree())
    }

}