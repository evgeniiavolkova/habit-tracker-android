package com.example.habits_tracker.database

import androidx.lifecycle.LiveData
import com.example.habits_tracker.Model.ActivityHistory

interface IActivityHistoryLocalRepository {
    fun getAll(): LiveData<MutableList<ActivityHistory>>
    suspend fun findById(id: Long): ActivityHistory
    suspend fun findByHabitId(habitId: Long): ActivityHistory
    suspend fun insertActivityHistory(activityHistory: ActivityHistory): Long
    suspend fun updateActivityHistory(activityHistory: ActivityHistory)
    suspend fun deleteActivityHistory(activityHistory: ActivityHistory)
    suspend fun getCountHabitsWithId(habitId: Long): Int
    suspend fun deleteFromActivityHistory(habitId: Long)
}