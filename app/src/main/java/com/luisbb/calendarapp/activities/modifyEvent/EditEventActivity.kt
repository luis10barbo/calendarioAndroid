package com.luisbb.calendarapp.activities.modifyEvent

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.luisbb.calendarapp.R
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import com.luisbb.calendarapp.utils.getDaysArray
import com.luisbb.calendarapp.utils.getHourArray
import com.luisbb.calendarapp.utils.getMinuteArray
import com.luisbb.calendarapp.utils.getMonthsArray
import com.luisbb.calendarapp.utils.getYearsArray
import com.luisbb.calendarapp.viewModels.activities.createEventActivity.ModifyEventViewModel
import com.luisbb.calendarapp.viewModels.activities.createEventActivity.CreateEventViewModelFactory
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class EditEventActivity: ModifyEventActivity() {
    private var dateEvent: DateEvent? = null
    private var eventId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getEventInformation()

    }

    override fun setupUi() {
        super.setupUi()
        setupTextView()
    }
    override fun getIntentExtras() {
        if (intent == null) return
        eventId = intent.getIntExtra("eventId" ,-1)
    }


    override fun setDefaults() {
        super.setDefaults()
        if (dateEvent == null) return
        updateLayoutVisibility()
    }

    private fun getEventInformation() {
        activityViewModel.eventId = eventId
        val result = activityViewModel.dateEventViewModel.getEvent(activityViewModel.eventId!!)
        result.observe(this) {dateEvent ->
            onDateEventLoad(dateEvent)
        }
    }
    private fun onDateEventLoad(dateEvent: DateEvent) {
        activityViewModel.startDateEvent = dateEvent
        activityViewModel.dateEvent = activityViewModel.startDateEvent.copy()

        if (dateEvent.hour != null || dateEvent.minute != null) useTime = true

        val hour = if (dateEvent.hour == null) 0 else dateEvent.hour!!
        val minute = if (dateEvent.minute == null) 0 else dateEvent.minute!!

        dateTime = ZonedDateTime.of(
            dateEvent.year,
            dateEvent.month,
            dateEvent.day,
            hour,
            minute,
            0,
            0,
            ZoneId.systemDefault()
        )


        setValues(activityViewModel.dateEvent)
    }

    private fun setupTextView() {
        val tvCreateEvent = findViewById<TextView>(R.id.tvCreateEvent)
        tvCreateEvent.text = resources.getString(R.string.edit_event)
    }
}