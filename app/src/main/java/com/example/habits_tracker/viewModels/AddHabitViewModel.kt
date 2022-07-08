package com.example.habits_tracker.viewModels

import androidx.lifecycle.ViewModel
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.database.IHabitLocalRepository

class AddHabitViewModel(private val repository: IHabitLocalRepository) : ViewModel() {
    var id: Long? = null
    var habit = Habit("", "")

    //var isNotificationOn

    suspend fun saveHabit(): Long? {
        if (id == null) {
            return repository.insertHabit(habit)
        } else {
            repository.updateHabit(habit)
        }
        return id
    }

    suspend fun findById(): Habit = repository.findById(id!!)

    suspend fun deleteHabit() = repository.deleteHabit(habit)

    suspend fun insertHabit() = repository.insertHabit(habit)

    suspend fun updateHabit() = repository.updateHabit(habit)

}