package com.example.habits_tracker.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "habits")
data class Habit(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "discription") var description: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "startDate")
    var startDate: Long? = null

    @ColumnInfo(name = "finishDate")
    var finishDate: Long? = null

    @ColumnInfo(name = "latitude")
    var latitude: Double? = 0.0

    @ColumnInfo(name = "longitude")
    var longitude: Double? = 0.0

    @ColumnInfo(name = "isNotificationOn")
    var isNotificationOn: Boolean = false

    @ColumnInfo(name = "notificationText")
    var notificationText: String? = null

    @ColumnInfo(name = "timeRemainder")
    var timeRemainder: Long? = null

    @ColumnInfo(name = "count")
    var count: Int = 0

    @ColumnInfo(name = "mondayRepeat")
    var mondayRepeat: Boolean = false

    @ColumnInfo(name = "tuesdayRepeat")
    var tuesdayRepeat: Boolean = false

    @ColumnInfo(name = "wednesdayRepeat")
    var wednesdayRepeat: Boolean = false

    @ColumnInfo(name = "thursdayRepeat")
    var thursdayRepeat: Boolean = false

    @ColumnInfo(name = "fridayRepeat")
    var fridayRepeat: Boolean = false

    @ColumnInfo(name = "saturdayRepeat")
    var saturdayRepeat: Boolean = false

    @ColumnInfo(name = "sundayRepeat")
    var sundayRepeat: Boolean = false

}
