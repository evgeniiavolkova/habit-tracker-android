package com.example.habits_tracker.viewModels

import androidx.lifecycle.ViewModel
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.database.IHabitLocalRepository
import java.util.*

class AddTimeViewModel(private val repository: IHabitLocalRepository): ViewModel() {
    var id: Long? = null
    var habit = Habit("", "")
    var time: Long? = null

    suspend fun saveHabit(){
        if (id == null){
            repository.insertHabit(habit)
        } else {
            repository.updateHabit(habit)
        }
    }
    suspend fun findById(): Habit = repository.findById(id!!)
    suspend fun updateHabit() = repository.updateHabit(habit)
}