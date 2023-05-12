package com.luisbb.calendarapp.activities

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.luisbb.calendarapp.R

class EditEventActivity: CreateEventActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tvCreateEvent = findViewById<TextView>(R.id.tvCreateEvent)
        tvCreateEvent.text = resources.getString(R.string.edit_event)
    }
}