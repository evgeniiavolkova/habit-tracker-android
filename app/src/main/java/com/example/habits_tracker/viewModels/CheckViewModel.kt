package com.example.habits_tracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import com.example.habits_tracker.Model.ActivityHistory
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.database.IActivityHistoryLocalRepository
import com.example.habits_tracker.database.IHabitLocalRepository

class CheckViewModel(private val activityRepository: IActivityHistoryLocalRepository,
                     private val habitRepository: IHabitLocalRepository): ViewModel() {
    var id: Long? = null
    var activityHistory= ActivityHistory(0, "", 0, 0, "")
    var habitId: Long? = null
    var habit = Habit("", "")

    suspend fun findHabitByHabitId(): Habit = habitRepository.findById(habitId!!)

    suspend fun getCountHabitsWithId() = activityRepository.getCountHabitsWithId(habitId!!)
    fun getAll(): LiveData<MutableList<ActivityHistory>> = activityRepository.getAll()
    suspend fun findByIdActivity(): ActivityHistory = activityRepository.findById(id!!)
    suspend fun findByHabitId(): ActivityHistory = activityRepository.findByHabitId(habitId!!)
    suspend fun insertActivityHistory(): Long = activityRepository.insertActivityHistory(activityHistory)
    suspend fun updateActivityHistory() = activityRepository.updateActivityHistory(activityHistory)
    suspend fun deleteActivityHistory() = activityRepository.deleteActivityHistory(activityHistory)

    suspend fun saveActivituEntery(): Long? {
        if (id == null) {
            return activityRepository.insertActivityHistory(activityHistory)
        } else {
            activityRepository.updateActivityHistory(activityHistory)
        }
        return null
    }
    suspend fun deleteHabit() = habitRepository.deleteHabit(habit)
    suspend fun deleteFromActivityHistory() = activityRepository.deleteFromActivityHistory(habitId!!)

}
