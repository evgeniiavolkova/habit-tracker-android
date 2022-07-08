package com.example.habits_tracker.di

import com.example.habits_tracker.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AddHabitViewModel(get())
    }
    viewModel{
        AddTimeViewModel(get())
    }
    viewModel {
        CheckViewModel(get(), get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel{
        StatisticViewModel(get(), get())
    }
    viewModel{
        TodayHabitsViewModel(get())
    }
}