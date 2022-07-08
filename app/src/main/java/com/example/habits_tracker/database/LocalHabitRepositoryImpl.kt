package com.example.habits_tracker.database

import androidx.lifecycle.LiveData
import com.example.habits_tracker.Model.Habit

class LocalHabitRepositoryImpl(private val dao: HabitDao) : IHabitLocalRepository  {
    override fun getAll(): LiveData<MutableList<Habit>> {
        return dao.getAll()
    }
    override suspend fun findById(id: Long): Habit {
        return dao.findById(id)
    }
    override suspend fun insertHabit(habit: Habit): Long {
        return dao.insertHabit(habit)
    }
    override suspend fun updateHabit(habit: Habit) {
        dao.updateHabit(habit)
    }
    override suspend fun deleteHabit(habit: Habit) {
        dao.deleteHabit(habit)
    }

    override suspend fun getCount(): Int {
        return dao.getCount()
    }

    override suspend fun getNumberOfWeekDay(): Int {
        return dao.getNumberOfWeekDay()
    }

    override suspend fun updateMondeyRepeat(id: Long, state: Boolean) {
        dao.updateMondeyRepeat(id, state)
    }

    override suspend fun updateTuesdayRepeat(id: Long, state: Boolean) {
        dao.updateTuesdayRepeat(id, state)
    }

    override suspend fun updatewednesdayRepeat(id: Long, state: Boolean) {
        dao.updatewednesdayRepeat(id, state)
    }

    override suspend fun updatethursdayRepeat(id: Long, state: Boolean) {
        dao.updatethursdayRepeat(id, state)
    }

    override suspend fun updatefridayRepeat(id: Long, state: Boolean) {
        dao.updatefridayRepeat(id, state)
    }

    override suspend fun updatesaturdayRepeat(id: Long, state: Boolean) {
        dao.updatesaturdayRepeat(id, state)
    }

    override suspend fun updatesundayRepeat(id: Long, state: Boolean) {
        dao.updatesundayRepeat(id, state)
    }

    override fun getMonday(): LiveData<MutableList<Habit>> {
        return dao.getMonday()
    }

    override fun getTuesday(): LiveData<MutableList<Habit>> {
        return dao.getTuesday()
    }

    override fun getWednesday(): LiveData<MutableList<Habit>> {
        return dao.getWednesday()
    }

    override fun getThursday(): LiveData<MutableList<Habit>> {
        return dao.getThursday()
    }

    override fun getFriday(): LiveData<MutableList<Habit>> {
        return dao.getFriday()
    }

    override fun getSaturday(): LiveData<MutableList<Habit>> {
        return dao.getSaturday()
    }

    override fun getSunday(): LiveData<MutableList<Habit>> {
        return dao.getSunday()
    }

}