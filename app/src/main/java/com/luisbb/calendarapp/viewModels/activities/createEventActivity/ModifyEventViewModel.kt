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

class ModifyEventViewModel(private val application: Application, private val activity: AppCompatActivity, val date: ZonedDateTime?): ViewModel() {
    lateinit var dateEventViewModel: DateEventViewModel
    var eventId: Int? = null

    @RequiresApi(Build.VERSION_CODES.O)
    var startDateEvent = DateEvent(
        eventId ?: 0,
        date?.year ?: 2000,
        date?.monthValue ?: 1,
        date?.dayOfMonth ?: 1,
        date?.hour,
        date?.minute,
        "",
        ""
    )
    @RequiresApi(Build.VERSION_CODES.O)
    var dateEvent = startDateEvent.copy()

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
    fun submitDateEvent() =  viewModelScope.launch {
        if (eventId == null)  {
            insertEvent()
            return@launch
        }
        replaceEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun replaceEvent() = viewModelScope.launch {
        dateEventViewModel.updateDateEvent(dateEvent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertEvent() = viewModelScope.launch {
        dateEventViewModel.insertDateEvent(dateEvent)
    }
}