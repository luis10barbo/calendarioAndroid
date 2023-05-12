package com.example.calendarapp.viewModels.activities.dateActivity

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.ZonedDateTime

class DateActivityViewModelFactory(private val application: Application, private val activity: AppCompatActivity, private val date: ZonedDateTime): ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DateActivityViewModel::class.java)) return DateActivityViewModel(application, activity, date) as T

        throw IllegalArgumentException("Unknown View Model Class")
    }
}