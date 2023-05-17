package com.luisbb.calendarapp.activities.modifyEvent

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.luisbb.calendarapp.R
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import com.luisbb.calendarapp.utils.epochSecondToLocalDate
import com.luisbb.calendarapp.utils.getDaysArray
import com.luisbb.calendarapp.utils.getHourArray
import com.luisbb.calendarapp.utils.getMinuteArray
import com.luisbb.calendarapp.utils.getMonthsArray
import com.luisbb.calendarapp.utils.getYearsArray
import com.luisbb.calendarapp.viewModels.activities.createEventActivity.CreateEventViewModelFactory
import com.luisbb.calendarapp.viewModels.activities.createEventActivity.ModifyEventViewModel
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZonedDateTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
abstract class ModifyEventActivity: AppCompatActivity() {
    lateinit var activityViewModel: ModifyEventViewModel

    var dateTime: ZonedDateTime = ZonedDateTime.now()


    lateinit var nameEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var daySpinner: Spinner
    lateinit var monthSpinner: Spinner
    lateinit var yearSpinner: Spinner
    lateinit var hourSpinner: Spinner
    lateinit var minuteSpinner: Spinner
    lateinit var timeLayout: LinearLayout
    private lateinit var useTimeSwitch: SwitchMaterial

    var useTime = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        getIntentExtras()
        setupViewModel()
        setupUi()

        setDefaults()
    }

    open fun setupUi() {
        setupEditText()
        setupSpinners()
        setupButtons()
        setupSwitches()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun getIntentExtras() {
        if (intent == null) return
        val epochSecond = intent.getLongExtra("date", 0)

        dateTime = epochSecondToLocalDate(epochSecond)
    }

    fun setupEditText() {
        nameEditText = findViewById(R.id.etEventName)
        descriptionEditText = findViewById(R.id.etEventDescription)
    }

    fun setupViewModel() {
        val factory = CreateEventViewModelFactory(application, this, dateTime)
        activityViewModel = ViewModelProvider(
            this,
            factory
        )[ModifyEventViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupSpinners() {
        daySpinner = findViewById(R.id.spnDay)
        monthSpinner = findViewById(R.id.spnMonth)
        yearSpinner = findViewById(R.id.spnYear)
        hourSpinner = findViewById(R.id.spnHour)
        minuteSpinner = findViewById(R.id.spnMinute)

        setDaysSpinner()
        monthSpinner.adapter = ArrayAdapter(this, R.layout.custom_spinner, getMonthsArray())
        yearSpinner.adapter = ArrayAdapter(this, R.layout.custom_spinner, getYearsArray())
        hourSpinner.adapter = ArrayAdapter(this, R.layout.custom_spinner, getHourArray())
        minuteSpinner.adapter = ArrayAdapter(this, R.layout.custom_spinner, getMinuteArray())
        Log.d("teste", "Waga")
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                activityViewModel.dateEvent.day = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activityViewModel.dateEvent.month = position + 1
                setDaysSpinner()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activityViewModel.dateEvent.year = position + 2000
                setDaysSpinner()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setDaysSpinner() {
        val daySpinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner, getDaysArray(YearMonth.of(activityViewModel.dateEvent.year, activityViewModel.dateEvent.month).lengthOfMonth()))

        daySpinner.adapter = daySpinnerAdapter
        if (daySpinnerAdapter.count > activityViewModel.dateEvent.day - 1)
            daySpinner.setSelection(activityViewModel.dateEvent.day - 1)
        else
            daySpinner.setSelection(0)
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

    open fun setDefaults() {
        if (activityViewModel == null) return
        Log.d("testewaga", "${activityViewModel.startDateEvent} \n${activityViewModel.dateEvent}")
        setValues(activityViewModel.startDateEvent)
    }

    fun setValues(dateEvent: DateEvent) {
        daySpinner.setSelection(dateEvent.day - 1)
        monthSpinner.setSelection(dateEvent.month - 1)
        yearSpinner.setSelection(dateEvent.year - 2000)
        hourSpinner.setSelection(dateEvent.hour ?: 0)
        minuteSpinner.setSelection(dateEvent.minute ?: 0)

        nameEditText.setText(dateEvent.name)
        descriptionEditText.setText(dateEvent.description)
        if (dateEvent.hour != null || dateEvent.minute != null) {
            useTimeSwitch.isChecked = true
            updateLayoutVisibility()
        }
    }

    open fun resetButtonClick() {
        setDefaults()
    }

    open fun saveButtonClick() {
        activityViewModel.dateEvent.name = nameEditText.text.toString()
        activityViewModel.dateEvent.description = descriptionEditText.text.toString()

        activityViewModel.dateEvent.year = yearSpinner.selectedItem.toString().toInt()
        activityViewModel.dateEvent.month = monthSpinner.selectedItemPosition + 1
        activityViewModel.dateEvent.day = daySpinner.selectedItem.toString().toInt()

        if (useTime) {
            activityViewModel.dateEvent.hour = hourSpinner.selectedItem.toString().toInt()
            activityViewModel.dateEvent.minute = minuteSpinner.selectedItem.toString().toInt()
        } else {
            activityViewModel.dateEvent.hour = null
            activityViewModel.dateEvent.minute = null
        }

        activityViewModel.submitDateEvent()
        this.finish()
    }

    open fun setupSwitches() {
        useTimeSwitch = findViewById(R.id.swUseTime)
        timeLayout = findViewById(R.id.lyTime)
        useTimeSwitch.setOnCheckedChangeListener { _, isChecked ->
            useTime = isChecked

            updateLayoutVisibility()
        }
    }

    fun updateLayoutVisibility() {
        if (useTime) timeLayout.visibility = View.VISIBLE
        else timeLayout.visibility = View.GONE
    }
}