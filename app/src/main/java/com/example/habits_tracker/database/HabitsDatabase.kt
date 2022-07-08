package com.example.habits_tracker.database

import android.content.Context
import androidx.room.*
import com.example.habits_tracker.Model.ActivityHistory
import com.example.habits_tracker.Model.Habit

@Database(entities = [Habit::class, ActivityHistory::class], version = 5, exportSchema = true)

abstract class HabitsDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun activityHistoryDao(): ActivityHistoryDao

    companion object {
        private var INSTANCE: HabitsDatabase? = null
        fun getDatabase(context: Context): HabitsDatabase {
            if (INSTANCE == null) {
                synchronized(HabitsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            HabitsDatabase::class.java, "habits_database"
                        ).fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()

                    }
                }
            }
            return INSTANCE!!
        }

    }
}