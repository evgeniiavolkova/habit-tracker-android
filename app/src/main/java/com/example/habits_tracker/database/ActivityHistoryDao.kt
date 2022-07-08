package com.example.habits_tracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits_tracker.Model.ActivityHistory

@Dao
interface ActivityHistoryDao {

    @Query("SELECT * FROM activityHistory")
    fun getAll(): LiveData<MutableList<ActivityHistory>>

    @Query("SELECT * FROM activityHistory WHERE id = :id")
    suspend fun findById(id: Long): ActivityHistory

    @Query("SELECT COUNT(*) FROM activityHistory WHERE habitId = :habitId")
    suspend fun getCountHabitsWithId(habitId: Long): Int

    @Query("SELECT * FROM activityHistory WHERE habitId = :habitId ORDER BY id DESC LIMIT 1")
    suspend fun findByHabitId(habitId: Long): ActivityHistory

    @Query("DELETE FROM activityHistory WHERE habitId = :habitId")
    suspend fun deleteFromActivityHistory(habitId: Long)

    @Insert
    suspend fun insertActivityHistory(activityHistory: ActivityHistory): Long

    @Update
    suspend fun updateActivityHistory(activityHistory: ActivityHistory)

    @Delete
    suspend fun deleteActivityHistory(activityHistory: ActivityHistory)
}