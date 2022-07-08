package com.example.habits_tracker.viewModels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.habits_tracker.Model.ActivityHistory
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.database.ActivityHistoryLocalImpl
import com.example.habits_tracker.database.IActivityHistoryLocalRepository
import com.example.habits_tracker.database.IHabitLocalRepository
import com.example.habits_tracker.utils.DateUtils
import java.time.LocalDate

class StatisticViewModel(private val repository: IHabitLocalRepository, private val activityHistoryRepository: IActivityHistoryLocalRepository): ViewModel() {
    var habitId: Long? = null
    var habit:Habit = Habit("", "")
    var activityHistoryId: Long? = null
    var activityHistory= ActivityHistory(0, "", 0, 0, "")

    var startDate: LocalDate? = null
    var endDate: LocalDate? = null

    var dayStart: Int? = null
    var monthStart: Int? = null
    var yearStart: Int? = null

    var dayFinish: Int? = null
    var monthFinish: Int? = null
    var yearFinish: Int? = null

    suspend fun findHabitByHabitId(): Habit = repository.findById(habitId!!)
    suspend fun findActivityByHabitId(): ActivityHistory = activityHistoryRepository.findByHabitId(habitId!!)
}