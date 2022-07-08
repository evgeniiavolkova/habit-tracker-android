package com.example.habits_tracker.di

import android.content.Context
import com.example.habits_tracker.database.HabitsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        providesDatabase(androidContext())
    }
}

fun providesDatabase(context: Context): HabitsDatabase = HabitsDatabase.getDatabase(context)