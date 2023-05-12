package com.luisbb.calendarapp.viewModels.dateEvent

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import com.luisbb.calendarapp.database.dao.DateEventDao
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class DateEventViewModel(private val dateEventDao: DateEventDao): ViewModel() {
    fun insertDateEvent(dateEvent: DateEvent) = viewModelScope.launch {
        dateEventDao.insertDateEvent(dateEvent)
    }

    fun deleteDateEvent(dateEvent: DateEvent) = viewModelScope.launch {
        dateEventDao.deleteDateEvent(dateEvent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthEvents(date: ZonedDateTime): LiveData<List<DateEvent>> {
        Log.d("teste", date.monthValue.toString())
        return dateEventDao.getMonthEvents(date.year, date.monthValue)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayEvents(date: ZonedDateTime): LiveData<List<DateEvent>> {
        return dateEventDao.getDateEvents(date.year, date.monthValue, date.dayOfMonth)
    }
}