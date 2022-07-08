package com.example.habits_tracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits_tracker.Model.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<MutableList<Habit>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun findById(id: Long): Habit

    @Insert
    suspend fun insertHabit(habit: Habit): Long

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT COUNT(*) FROM habits")
    suspend fun getCount(): Int

 // выбрать если в этот день надо выполнять что то
    @Query("SELECT strftime('%w',DATE()) ")
    suspend fun getNumberOfWeekDay():Int

    @Query("UPDATE habits SET mondayRepeat = :state WHERE id = :id")
    suspend fun updateMondeyRepeat(id: Long, state: Boolean)

    @Query("UPDATE habits SET tuesdayRepeat = :state WHERE id = :id")
    suspend fun updateTuesdayRepeat(id: Long, state: Boolean)

    @Query("UPDATE habits SET wednesdayRepeat = :state WHERE id = :id")
    suspend fun updatewednesdayRepeat(id: Long, state: Boolean)

    @Query("UPDATE habits SET thursdayRepeat = :state WHERE id = :id")
    suspend fun updatethursdayRepeat(id: Long, state: Boolean)

    @Query("UPDATE habits SET fridayRepeat = :state WHERE id = :id")
    suspend fun updatefridayRepeat(id: Long, state: Boolean)

    @Query("UPDATE habits SET saturdayRepeat = :state WHERE id = :id")
    suspend fun updatesaturdayRepeat(id: Long, state: Boolean)

    @Query("UPDATE habits SET sundayRepeat = :state WHERE id = :id")
    suspend fun updatesundayRepeat(id: Long, state: Boolean)

    @Query("SELECT * FROM habits WHERE mondayRepeat = 1")
    fun getMonday(): LiveData<MutableList<Habit>>

    @Query("SELECT * FROM habits WHERE tuesdayRepeat = 1")
    fun getTuesday(): LiveData<MutableList<Habit>>

    @Query("SELECT * FROM habits WHERE wednesdayRepeat = 1")
    fun getWednesday(): LiveData<MutableList<Habit>>

    @Query("SELECT * FROM habits WHERE thursdayRepeat = 1")
    fun getThursday(): LiveData<MutableList<Habit>>

    @Query("SELECT * FROM habits WHERE fridayRepeat = 1")
    fun getFriday(): LiveData<MutableList<Habit>>
    @Query("SELECT * FROM habits WHERE saturdayRepeat = 1")
    fun getSaturday(): LiveData<MutableList<Habit>>

    @Query("SELECT * FROM habits WHERE sundayRepeat = 1")
    fun getSunday(): LiveData<MutableList<Habit>>

}