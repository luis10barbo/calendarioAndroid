package com.luisbb.calendarapp.viewModels.activities.mainActivity

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calendarapp.database.dao.DateEventDatabase
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import com.luisbb.calendarapp.viewModels.dateEvent.DateEventViewModel
import com.luisbb.calendarapp.viewModels.dateEvent.DateEventViewModelFactory
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class MainActivityViewModel(private val application: Application, private val activity: AppCompatActivity): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    var currentDate: ZonedDateTime = ZonedDateTime.now()
    lateinit var dateEventViewModel: DateEventViewModel
    lateinit var monthEvents: LiveData<List<DateEvent>>
    lateinit var upcomingEvents: LiveData<List<DateEvent>>

    init {
        setupDateEventViewModel()
        getMonthEvents()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthEvents() {
        monthEvents = dateEventViewModel.getMonthEvents(currentDate)
        upcomingEvents = dateEventViewModel.getMonthUpcomingEvents(currentDate)
    }

    private fun setupDateEventViewModel() {
        val dateEventDao = DateEventDatabase.getInstance(application).dateEventDao()
        val dateEventViewModelFactory = DateEventViewModelFactory(dateEventDao)
        dateEventViewModel = ViewModelProvider(
            activity,
            dateEventViewModelFactory
        )[DateEventViewModel::class.java]
    }


}