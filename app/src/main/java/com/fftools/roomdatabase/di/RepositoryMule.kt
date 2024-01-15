package com.fftools.roomdatabase.di


import com.fftools.roomdatabase.database.DatabaseHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { DatabaseHelper.getInstance(androidContext()).noteDao() }
}
