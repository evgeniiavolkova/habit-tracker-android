package com.example.habits_tracker

import android.app.Application
import com.example.habits_tracker.di.daoModule
import com.example.habits_tracker.di.databaseModule
import com.example.habits_tracker.di.repositoryModule
import com.example.habits_tracker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HabitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                databaseModule,
                daoModule,
                repositoryModule,
                viewModelModule)
        }

    }
}