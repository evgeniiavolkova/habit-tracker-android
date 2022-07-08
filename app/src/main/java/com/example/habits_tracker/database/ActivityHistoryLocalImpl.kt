package com.example.habits_tracker.database

import androidx.lifecycle.LiveData
import com.example.habits_tracker.Model.ActivityHistory

class ActivityHistoryLocalImpl(private val dao: ActivityHistoryDao) :
    IActivityHistoryLocalRepository {

    override fun getAll(): LiveData<MutableList<ActivityHistory>> {
        return dao.getAll()
    }

    override suspend fun findById(id: Long): ActivityHistory {
        return dao.findById(id)
    }

    override suspend fun findByHabitId(habitId: Long): ActivityHistory {
        return dao.findByHabitId(habitId)
    }

    override suspend fun insertActivityHistory(activityHistory: ActivityHistory): Long {
        return dao.insertActivityHistory(activityHistory)
    }

    override suspend fun updateActivityHistory(activityHistory: ActivityHistory) {
        return dao.updateActivityHistory(activityHistory)
    }

    override suspend fun deleteActivityHistory(activityHistory: ActivityHistory) {
        return dao.deleteActivityHistory(activityHistory)
    }

    override suspend fun getCountHabitsWithId(habitId: Long): Int {
        return dao.getCountHabitsWithId(habitId)
    }

    override suspend fun deleteFromActivityHistory(habitId: Long) {
        dao.deleteFromActivityHistory(habitId)
    }
}