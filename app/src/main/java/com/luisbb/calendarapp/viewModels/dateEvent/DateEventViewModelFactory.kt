package com.luisbb.calendarapp.viewModels.dateEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.luisbb.calendarapp.database.dao.DateEventDao

class DateEventViewModelFactory(private val dateEventDao: DateEventDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DateEventViewModel::class.java)) return DateEventViewModel(dateEventDao) as T

        throw IllegalArgumentException("Unknown View Model Class")
    }
}