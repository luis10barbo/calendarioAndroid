package com.example.calendarapp.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.calendarapp.R
import com.example.calendarapp.utils.epochSecondToLocalDate
import com.example.calendarapp.utils.getDaysArray
import com.example.calendarapp.utils.getHourArray
import com.example.calendarapp.utils.getMinuteArray
import com.example.calendarapp.utils.getMonthsArray
import com.example.calendarapp.utils.getYearsArray
import com.example.calendarapp.viewModels.activities.createEventActivity.CreateEventViewModel
import com.example.calendarapp.viewModels.activities.createEventActivity.CreateEventViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial
import java.time.ZonedDateTime

class CreateEventActivity: AppCompatActivity() {

    private lateinit var activityViewModel: CreateEventViewModel
    private lateinit var startDateTime: ZonedDateTime
    private lateinit var dateTime: ZonedDateTime

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText

    private lateinit var daySpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var hourSpinner: Spinner
    private lateinit var minuteSpinner: Spinner

    private var useTime = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        if (intent == null) return
        val epochSecond = intent.getLongExtra("date", 0)

        startDateTime = epochSecondToLocalDate(epochSecond)
        dateTime = startDateTime

        setupEditText()
        setupViewModel()
        setupSpinners()
        setupButtons()
        setupSwitches()
    }

    private fun setupEditText() {
        nameEditText = findViewById(R.id.etEventName)
        descriptionEditText = findViewById(R.id.etEventDescription)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewModel() {
        val factory = CreateEventViewModelFactory(application, this, dateTime)
        activityViewModel = ViewModelProvider(
            this,
            factory
        )[CreateEventViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupSpinners() {
        daySpinner = findViewById(R.id.spnDay)
        monthSpinner = findViewById(R.id.spnMonth)
        yearSpinner = findViewById(R.id.spnYear)
        hourSpinner = findViewById(R.id.spnHour)
        minuteSpinner = findViewById(R.id.spnMinute)

        val dayAdapter = ArrayAdapter(this, R.layout.custom_spinner, getDaysArray(dateTime.toLocalDate()))
        val monthAdapter = ArrayAdapter(this, R.layout.custom_spinner, getMonthsArray())
        val yearAdapter = ArrayAdapter(this, R.layout.custom_spinner, getYearsArray())
        val hourAdapter = ArrayAdapter(this, R.layout.custom_spinner, getHourArray())
        val minuteAdapter = ArrayAdapter(this, R.layout.custom_spinner, getMinuteArray())

        daySpinner.adapter = dayAdapter
        monthSpinner.adapter = monthAdapter
        yearSpinner.adapter = yearAdapter
        hourSpinner.adapter = hourAdapter
        minuteSpinner.adapter = minuteAdapter

        daySpinner.setSelection(dateTime.dayOfMonth - 1)
        monthSpinner.setSelection(activityViewModel.date.monthValue - 1)
        yearSpinner.setSelection(activityViewModel.date.year - 2000)
        hourSpinner.setSelection(dateTime.hour)
        minuteSpinner.setSelection(dateTime.minute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupButtons() {
        val saveButton = findViewById<Button>(R.id.btnSave)
        val resetButton = findViewById<Button>(R.id.btnReset)

        saveButton.setOnClickListener {
            saveButtonClick()
        }

        resetButton.setOnClickListener {
            resetButtonClick()
        }
    }

    private fun setupSwitches() {
        val useTimeSwitch = findViewById<SwitchMaterial>(R.id.swUseTime)
        val timeLayout = findViewById<LinearLayout>(R.id.lyTime)
        useTimeSwitch.setOnCheckedChangeListener { _, isChecked ->
            useTime = isChecked

            if (useTime) timeLayout.visibility = View.VISIBLE
            else timeLayout.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveButtonClick() {
        activityViewModel.eventName = nameEditText.text.toString()
        activityViewModel.eventDescription = descriptionEditText.text.toString()

        activityViewModel.year = yearSpinner.selectedItem.toString().toInt()
        activityViewModel.month = monthSpinner.selectedItemPosition + 1
        activityViewModel.day = daySpinner.selectedItem.toString().toInt()

        if (useTime) {
            activityViewModel.hour = hourSpinner.selectedItem.toString().toInt()
            activityViewModel.minute = minuteSpinner.selectedItem.toString().toInt()
        }

        activityViewModel.saveEvent()
        this.finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun resetButtonClick() {
        dateTime = startDateTime
        setupSpinners()
    }
}