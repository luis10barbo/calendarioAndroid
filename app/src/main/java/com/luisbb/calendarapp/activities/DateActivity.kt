package com.luisbb.calendarapp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luisbb.calendarapp.R
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import com.luisbb.calendarapp.recycleViews.EventsRecycleViewAdapter
import com.luisbb.calendarapp.utils.epochSecondToLocalDate
import com.luisbb.calendarapp.utils.getDaysOfWeekArray
import com.luisbb.calendarapp.utils.getMonthsArray
import com.luisbb.calendarapp.utils.localDateToEpochSecond
import com.luisbb.calendarapp.viewModels.activities.dateActivity.DateActivityViewModel
import com.luisbb.calendarapp.viewModels.activities.dateActivity.DateActivityViewModelFactory
import java.time.ZonedDateTime

class DateActivity: AppCompatActivity() {
    private lateinit var date: ZonedDateTime
    private lateinit var dateActivityViewModel: DateActivityViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        getIntentExtras()
        setupActivityViewModel()

        setupButtons()
        setupTextViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getDateEvents()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getIntentExtras() {
        if (intent == null) return;
        val epochSecond = intent.getLongExtra("date", 0)

        date = epochSecondToLocalDate(epochSecond)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupActivityViewModel() {
        val factory = DateActivityViewModelFactory(application, this, date)
        dateActivityViewModel = ViewModelProvider(
            this,
            factory
        )[DateActivityViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupTextViews() {
        val currentDay = getDaysOfWeekArray()[date.dayOfWeek.value - 1]
        val dateText = findViewById<TextView>(R.id.tvDate)
        dateText.text = "${currentDay}, ${date.dayOfMonth} de ${getMonthsArray()[date.monthValue - 1]} de ${date.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupButtons() {
        val createEventButton = findViewById<Button>(R.id.btnCreateEvent)
        createEventButton.setOnClickListener {
            createEvent()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateEvents() {
        dateActivityViewModel.getMonthEvents()
        dateActivityViewModel.dateEvents.observe(this) {dateEvents ->
            setupEventView(dateEvents)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupEventView(dateEvents: List<DateEvent>?) {
        if (dateEvents == null) return;

        val eventRecyclerView = findViewById<RecyclerView>(R.id.rvDateEvents)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)
        eventRecyclerView.adapter = EventsRecycleViewAdapter(dateEvents, this@DateActivity) {dateEvent ->
            eventClearClick(dateEvent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun eventClearClick(dateEvent: DateEvent) {
        dateActivityViewModel.dateEventViewModel.deleteDateEvent(dateEvent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createEvent() {
        val intent = Intent(this@DateActivity, CreateEventActivity::class.java)
        intent.putExtra("date", localDateToEpochSecond(date))
        startActivity(intent)
    }
}