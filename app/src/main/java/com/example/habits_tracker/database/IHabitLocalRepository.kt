package com.example.habits_tracker.database

import androidx.lifecycle.LiveData
import com.example.habits_tracker.Model.Habit

interface IHabitLocalRepository {
    fun getAll(): LiveData<MutableList<Habit>>
    suspend fun findById(id: Long): Habit
    suspend fun insertHabit(habit: Habit): Long
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    suspend fun getCount(): Int
    suspend fun getNumberOfWeekDay():Int
    suspend fun updateMondeyRepeat(id: Long, state: Boolean)
    suspend fun updateTuesdayRepeat(id: Long, state: Boolean)
    suspend fun updatewednesdayRepeat(id: Long, state: Boolean)
    suspend fun updatethursdayRepeat(id: Long, state: Boolean)
    suspend fun updatefridayRepeat(id: Long, state: Boolean)
    suspend fun updatesaturdayRepeat(id: Long, state: Boolean)
    suspend fun updatesundayRepeat(id: Long, state: Boolean)
    fun getMonday(): LiveData<MutableList<Habit>>
    fun getTuesday(): LiveData<MutableList<Habit>>
    fun getWednesday(): LiveData<MutableList<Habit>>
    fun getThursday(): LiveData<MutableList<Habit>>
    fun getFriday(): LiveData<MutableList<Habit>>
    fun getSaturday(): LiveData<MutableList<Habit>>
    fun getSunday(): LiveData<MutableList<Habit>>
}