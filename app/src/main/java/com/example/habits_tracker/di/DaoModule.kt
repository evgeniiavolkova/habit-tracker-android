package com.example.habits_tracker.di

import com.example.habits_tracker.database.ActivityHistoryDao
import com.example.habits_tracker.database.HabitDao
import com.example.habits_tracker.database.HabitsDatabase
import org.koin.dsl.module

val daoModule = module{
    single {
        provideHabitDao(get())
    }
    single {
        provideActivityHistoryDao(get())
    }
}

fun provideHabitDao(db: HabitsDatabase): HabitDao = db.habitDao()
fun provideActivityHistoryDao(db: HabitsDatabase): ActivityHistoryDao = db.activityHistoryDao()