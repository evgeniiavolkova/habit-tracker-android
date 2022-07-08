package com.example.habits_tracker.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activityHistory")
data class ActivityHistory(
    @ColumnInfo(name = "habitId") var habitId: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "count") var count: Int,
    @ColumnInfo(name = "dayOfWeek") var dayOfWeek: Int,
    @ColumnInfo(name = "description") var description: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "createdDate")
    var createdDate: Long? = null
}

// habit 4 steps 10 000 steps perDay 10.05.2022
// actId: 1, habId 4, titl: steps, count: 1, dayIfWeek: 5 (Friday), dascr.: 10 000 steps perDay, createDate:10.05.2022
// actID: 2, habId 4, titl: steps, count: 2, dayIfWeek: 6 (Saturday), dascr.: 10 000 steps perDay, createDate:11.05.2022
// actID: 3, habId 4, titl: steps, count: 3, dayIfWeek: 1 (Monday), dascr.: 10 000 steps perDay, createDate:13.05.2022
// то есть история обновляется только в те дни когда пользователь отмечает это
// чтобы вывести статискиту надо посомтреть