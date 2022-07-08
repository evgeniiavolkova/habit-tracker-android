package com.example.habits_tracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.database.IHabitLocalRepository

class TodayHabitsViewModel(private val repository: IHabitLocalRepository): ViewModel() {
    fun getAll(): LiveData<MutableList<Habit>> {
        return repository.getAll()
    }
    fun getMondeyHabits(): LiveData<MutableList<Habit>> {
        return repository.getMonday()
    }
    fun getTuesdayHabits(): LiveData<MutableList<Habit>> {
        return repository.getTuesday()
    }
    fun getWednesdayHabits(): LiveData<MutableList<Habit>> {
        return repository.getWednesday()
    }
    fun getThursdayHabits(): LiveData<MutableList<Habit>> {
        return repository.getThursday()
    }
    fun getFridayHabits(): LiveData<MutableList<Habit>> {
        return repository.getFriday()
    }
    fun getSaturdayHabits(): LiveData<MutableList<Habit>> {
        return repository.getSaturday()
    }
    fun getSundayHabits(): LiveData<MutableList<Habit>> {
        return repository.getSunday()
    }
    fun getList(day: Int): LiveData<MutableList<Habit>>{
        return when(day){
            1-> repository.getSunday()
            2-> repository.getMonday()
            3-> repository.getTuesday()
            4-> repository.getWednesday()
            5-> repository.getThursday()
            6-> repository.getFriday()
            7-> repository.getSaturday()
            else-> repository.getAll()
        }
    }

}