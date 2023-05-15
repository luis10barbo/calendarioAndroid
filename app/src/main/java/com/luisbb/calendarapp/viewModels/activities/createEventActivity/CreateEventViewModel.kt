package com.luisbb.calendarapp.viewModels.activities.createEventActivity

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.database.dao.DateEventDatabase
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import com.luisbb.calendarapp.viewModels.dateEvent.DateEventViewModel
import com.luisbb.calendarapp.viewModels.dateEvent.DateEventViewModelFactory
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CreateEventViewModel(private val application: Application, private val activity: AppCompatActivity, val date: ZonedDateTime): ViewModel() {
    lateinit var dateEventViewModel: DateEventViewModel

    var eventName = ""
    var eventDescription = ""
    var eventId: Int? = null

    @RequiresApi(Build.VERSION_CODES.O)
    var day = date.dayOfMonth
    @RequiresApi(Build.VERSION_CODES.O)
    var month = date.monthValue
    @RequiresApi(Build.VERSION_CODES.O)
    var year = date.year

    var hour: Int? = null
    var minute: Int? = null

    init {
        setupDateEventViewModel()
    }

    private fun setupDateEventViewModel() {
        val dateEventDao = DateEventDatabase.getInstance(application).dateEventDao()
        val dateEventViewModelFactory = DateEventViewModelFactory(dateEventDao)
        dateEventViewModel = ViewModelProvider(
            activity,
            dateEventViewModelFactory
        )[DateEventViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createThisDateEvent(): DateEvent {
        return DateEvent(
            eventId ?: 0,
            year,
            month,
            day,
            hour,
            minute,
            eventName,
            eventDescription
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitDateEvent() =  viewModelScope.launch {
        if (eventId == null)  {
            insertEvent()
            return@launch
        }
        replaceEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun replaceEvent() = viewModelScope.launch {
        val dateEvent = createThisDateEvent()
        dateEventViewModel.updateDateEvent(dateEvent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertEvent() = viewModelScope.launch {
        val dateEvent = createThisDateEvent()
        dateEventViewModel.insertDateEvent(dateEvent)
    }
}