package com.example.habits_tracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.database.IHabitLocalRepository

class MainViewModel(private val repository: IHabitLocalRepository): ViewModel() {
    fun getAll(): LiveData<MutableList<Habit>> {
        return repository.getAll()
    }
    suspend fun getCount():Int = repository.getCount()

    // чтобы можно было показать какие habit надо выполнить сегодня
    suspend fun getNumberOfWeekDay():Int = repository.getNumberOfWeekDay()
}