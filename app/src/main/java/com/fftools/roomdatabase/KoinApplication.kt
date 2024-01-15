package com.fftools.roomdatabase

import android.app.Application
import com.fftools.roomdatabase.di.repositoryModule
import com.fftools.roomdatabase.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApplication)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}