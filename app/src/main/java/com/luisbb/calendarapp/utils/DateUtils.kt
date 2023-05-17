package com.luisbb.calendarapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.luisbb.calendarapp.dataClasses.CalendarDay
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun localDateToEpochSecond(date: ZonedDateTime): Long {

    return date.toEpochSecond()
}

@RequiresApi(Build.VERSION_CODES.O)
fun epochSecondToLocalDate(epochSeconds: Long): ZonedDateTime {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault())
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDaysArray(lengthOfMonth: Int): Array<Int> {
    return (1..lengthOfMonth).toList().toTypedArray()
}

fun getYearsArray(): Array<Int> {
    return (2000..2150).toList().toTypedArray()
}

fun getMonthsArray(): Array<String> {
    return arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
}

fun getDaysOfWeekArray(): Array<String> {
    return arrayOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado")
}

fun getHourArray(): Array<Int> {
    return (0..24).toList().toTypedArray()
}

fun getMinuteArray(): Array<Int> {
    return (0..60).toList().toTypedArray()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCalendarDays(date: ZonedDateTime): List<CalendarDay> {
    val daysList = mutableListOf<CalendarDay>()
    val firstDayMonth = date.withDayOfMonth(1)
    val lastMonth = date.minusMonths(1)
    val lastDayMonth = lastMonth.withDayOfMonth(lastMonth.toLocalDate().lengthOfMonth())

    for (i in 0..41) {
        if (i < firstDayMonth.dayOfWeek.value) {
            // last Month
            daysList.add(
                CalendarDay(
                lastDayMonth.minusDays((firstDayMonth.dayOfWeek.value - i - 1).toLong())
            )
            )
            continue
        }
        // current month
        daysList.add(CalendarDay(firstDayMonth.plusDays((i - firstDayMonth.dayOfWeek.value).toLong())))
    }
    return daysList
}