package com.example.calendarapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.calendarapp.dataClasses.CalendarDay
import java.time.ZonedDateTime

class DayUtils {

}

@RequiresApi(Build.VERSION_CODES.O)
fun getDays(date: ZonedDateTime): List<CalendarDay> {
    val daysList = mutableListOf<CalendarDay>()
    val currentMonthLength = date.toLocalDate().lengthOfMonth()
    val firstDayMonth = date.withDayOfMonth(1)
    val lastMonth = date.minusMonths(1)
    val lastDayMonth = lastMonth.withDayOfMonth(lastMonth.toLocalDate().lengthOfMonth())
    for (i in 0..41) {
        if (i < firstDayMonth.dayOfWeek.value) {
            // last Month
            daysList.add(CalendarDay(
                lastDayMonth.minusDays((firstDayMonth.dayOfWeek.value - i - 1).toLong())
            ))
        }
        else {
            // current month
            daysList.add(CalendarDay(firstDayMonth.plusDays((i - firstDayMonth.dayOfWeek.value).toLong())))

        }

    }
    return daysList
}