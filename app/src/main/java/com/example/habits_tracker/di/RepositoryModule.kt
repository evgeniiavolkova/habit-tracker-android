package com.example.habits_tracker.di

import com.example.habits_tracker.database.*
import org.koin.dsl.module

val repositoryModule = module {
    single {
        provideLocalRidesrepository(get())
    }
    single {
        provideActivityHistoryLocalImpl(get())
    }
}

fun provideLocalRidesrepository(dao: HabitDao): IHabitLocalRepository =
    LocalHabitRepositoryImpl(dao)

fun provideActivityHistoryLocalImpl(activityHistoryDao: ActivityHistoryDao): IActivityHistoryLocalRepository =
    ActivityHistoryLocalImpl(activityHistoryDao)