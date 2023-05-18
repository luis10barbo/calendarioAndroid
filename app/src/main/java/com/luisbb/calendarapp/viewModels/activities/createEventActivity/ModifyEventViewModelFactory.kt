package com.luisbb.calendarapp.viewModels.activities.createEventActivity

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.ZonedDateTime

class ModifyEventViewModelFactory(private val application: Application, private val activity: AppCompatActivity, private val date: ZonedDateTime): ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModifyEventViewModel::class.java)) return ModifyEventViewModel(application, activity, date) as T

        throw IllegalArgumentException("Unknown View Model Class")
    }
}