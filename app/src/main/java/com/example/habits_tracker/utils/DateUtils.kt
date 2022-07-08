package com.example.habits_tracker.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun getDateString(unixTime: Long): String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = unixTime
            val format: SimpleDateFormat;
            format = SimpleDateFormat("yyyy/MM/dd", Locale.GERMAN)
            return format.format(calendar.getTime())
        }
        fun getDataStringForStatictics(unixTime: Long): String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = unixTime
            val format: SimpleDateFormat;
            format = SimpleDateFormat("d/MM/yyyy", Locale.GERMAN)
            return format.format(calendar.getTime())
        }
        fun getTimeString(unixTime: Long): String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = unixTime
            val format: SimpleDateFormat;
            format = SimpleDateFormat("HH:mm", Locale.GERMAN)
            return format.format(calendar.getTime())
        }

        fun getUnixTime(year: Int, month: Int, day: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return calendar.timeInMillis
        }

        fun getTime(hour: Int, minut: Int): Long{
            var defDate:Calendar = Calendar.getInstance()
            defDate.set(2022, 1,1,hour, minut)
            return defDate.timeInMillis
        }

         fun getHours(date: Long): Int{
            val date:Date = Date(date)
            val cal = Calendar.getInstance()
            cal.time = date
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            return hours
        }

        fun getMinuts(date: Long): Int{
            val date:Date = Date(date)
            val cal = Calendar.getInstance()
            cal.time = date
            val minuts = cal.get(Calendar.MINUTE)
            return minuts
        }

        fun getDay(longDate: Long):Int{
            val date:Date = Date(longDate)
            val cal = Calendar.getInstance()
            cal.time = date
            val days = cal.get(Calendar.DAY_OF_MONTH)
            return  days
        }

        fun getMonth(longDate: Long): Int{
            val date:Date = Date(longDate)
            val cal = Calendar.getInstance()
            cal.time = date
            val month = cal.get(Calendar.DAY_OF_MONTH)
            return month
        }
        fun getYear(longDate: Long): Int{
            val date:Date = Date(longDate)
            val cal = Calendar.getInstance()
            cal.time = date
            val year = cal.get(Calendar.YEAR)
            return year
        }
    }
}