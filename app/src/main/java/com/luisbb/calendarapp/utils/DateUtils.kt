package com.example.calendarapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
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
fun getDaysArray(date: LocalDate): Array<Int> {
    return (1..date.lengthOfMonth()).toList().toTypedArray()
}

fun getYearsArray(): Array<Int> {
    return (2000..2150).toList().toTypedArray()
}

fun getMonthsArray(): Array<String> {
    return arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
}

fun getHourArray(): Array<Int> {
    return (0..24).toList().toTypedArray()
}

fun getMinuteArray(): Array<Int> {
    return (0..60).toList().toTypedArray()
}