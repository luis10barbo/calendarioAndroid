package com.luisbb.calendarapp.recycleViews

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.luisbb.calendarapp.R
import com.luisbb.calendarapp.activities.modifyEvent.EditEventActivity
import com.luisbb.calendarapp.dataClasses.db.DateEvent

class EventsRecycleViewAdapter(private val dateEvents: List<DateEvent>, private val application: AppCompatActivity, private val onClearClick: (dateEvent:DateEvent) -> Unit): RecyclerView.Adapter<EventsRecycleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsRecycleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_date_events, parent, false)
        return EventsRecycleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dateEvents.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EventsRecycleViewHolder, position: Int) {
        holder.bind(dateEvents[position], onClearClick) { dateEvent ->
            onEditClick(dateEvent)


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onEditClick(dateEvent: DateEvent) {
        val intent = Intent(application, EditEventActivity::class.java)
        intent.putExtra("eventId", dateEvent.id)
        application.startActivity(intent)
    }

}

class EventsRecycleViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(dateEvent: DateEvent, onClearClick: (dateEvent: DateEvent) -> Unit, onEditClick: (dateEvent: DateEvent) -> Unit) {
        val nameTextView = view.findViewById<TextView>(R.id.tvEventName)
        val descTextView = view.findViewById<TextView>(R.id.tvEventDesc)
        val dateTextView = view.findViewById<TextView>(R.id.tvEventDate)
        val timeTextView = view.findViewById<TextView>(R.id.tvEventTime)


        val editButton = view.findViewById<TextView>(R.id.btnEventInfo)
        val cancelButton = view.findViewById<TextView>(R.id.btnEventCancel)

        nameTextView.text = dateEvent.name
        dateTextView.text = "${dateEvent.day}/${dateEvent.month}/${dateEvent.year}"
        if (dateEvent.description != null) descTextView.text = dateEvent.description
        if (dateEvent.hour != null && dateEvent.minute != null) {
            timeTextView.text = "Às ${dateEvent.hour}:${dateEvent.minute}"
        }

        editButton.setOnClickListener {
            onEditClick(dateEvent)
        }

        cancelButton.setOnClickListener {
            onClearClick(dateEvent)
        }
    }
}