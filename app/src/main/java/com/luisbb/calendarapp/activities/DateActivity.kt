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
import com.example.calendarapp.R
import com.example.calendarapp.activities.CreateEventActivity
import com.example.calendarapp.dataClasses.db.DateEvent
import com.example.calendarapp.recycleViews.EventsRecycleViewAdapter
import com.example.calendarapp.utils.epochSecondToLocalDate
import com.example.calendarapp.utils.getMonthsArray
import com.example.calendarapp.utils.localDateToEpochSecond
import com.example.calendarapp.viewModels.activities.dateActivity.DateActivityViewModel
import com.example.calendarapp.viewModels.activities.dateActivity.DateActivityViewModelFactory
import java.time.ZonedDateTime

class DateActivity: AppCompatActivity() {
    private lateinit var date: ZonedDateTime
    private lateinit var dateActivityViewModel: DateActivityViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)


        if (intent == null) return;
        val epochSecond = intent.getLongExtra("date", 0)

        date = epochSecondToLocalDate(epochSecond)

        val factory = DateActivityViewModelFactory(application, this, date)
        dateActivityViewModel = ViewModelProvider(
            this,
            factory
        )[DateActivityViewModel::class.java]

        setupButtons()
        setupTextViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getDateEvents()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupTextViews() {
        val dateText = findViewById<TextView>(R.id.tvDate)
        dateText.text = "${date.dayOfMonth} de ${getMonthsArray()[date.monthValue - 1]} de ${date.year}"
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
        eventRecyclerView.adapter = EventsRecycleViewAdapter(dateEvents) {dateEvent ->
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