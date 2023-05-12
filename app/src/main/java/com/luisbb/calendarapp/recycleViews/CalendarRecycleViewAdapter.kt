package com.example.calendarapp

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.dataClasses.CalendarDay
import com.example.calendarapp.dataClasses.db.DateEvent
import com.example.calendarapp.utils.getDays
import java.time.LocalDate
import java.time.ZonedDateTime

class CalendarRecycleViewAdapter(
    private val currentDate: ZonedDateTime,
    private val calendarDayClickListener: (calendarDay: CalendarDay) -> Unit,
    private val monthEvents: List<DateEvent>,
    private val whiteColorState: ColorStateList,
    private val selectedColorState: ColorStateList,
    private val blackColorState: ColorStateList,
    private val eventCurrentDateColorState: ColorStateList
): RecyclerView.Adapter<CalendarRecycleViewHolder>() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val calendarDays = getDays(currentDate)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarRecycleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listDays = layoutInflater.inflate(R.layout.list_days, parent, false)
        return CalendarRecycleViewHolder(listDays, currentDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemCount(): Int {
        return calendarDays.size + 7
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarRecycleViewHolder, position: Int) {
        val daysWeek = arrayOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado")

        if (position in 0..6) {
            holder.bindDayOfWeek(daysWeek[position])
            return;
        }
        val currentDate = calendarDays[position - 7]
        val daysWithEvents = mutableSetOf<Int>()
        for (monthEvent in monthEvents) {
             daysWithEvents.add(monthEvent.day)
        }

        val today = LocalDate.now()
        val dateIsToday = (today.dayOfMonth == currentDate.date.dayOfMonth && today.monthValue == currentDate.date.monthValue && today.year == currentDate.date.year)
        val dateIsThisMonth = currentDate.date.monthValue == this.currentDate.monthValue
        val dateHasEvents = daysWithEvents.contains(currentDate.date.dayOfMonth)

        if (dateIsThisMonth && dateHasEvents) {
            if (dateIsToday) {
                holder.bindCurrentDayWithEvent(currentDate, calendarDayClickListener,
                    this.currentDate, eventCurrentDateColorState)
            } else {
                holder.bindEventDate(currentDate, calendarDayClickListener,
                    this.currentDate, selectedColorState)
            }
        } else if (dateIsToday) {
            holder.bindCurrentDay(currentDate, calendarDayClickListener,
                this.currentDate, whiteColorState, blackColorState)
        }
        else {
            holder.bind(currentDate, calendarDayClickListener, this.currentDate)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
class CalendarRecycleViewHolder(private val view: View, private val currentDate: ZonedDateTime): RecyclerView.ViewHolder(view) {

    fun bindDayOfWeek(dayOfWeek: String) {
        val button = view.findViewById<Button>(R.id.btnCalendarDay)
        button.text = dayOfWeek[0].toString()
        button.setBackgroundColor(Color.TRANSPARENT)
    }

    fun bind(calendarDay: CalendarDay, calendarDayClickListener: (calendarDay: CalendarDay) -> Unit, currentDate: ZonedDateTime): Button {
        val button = view.findViewById<Button>(R.id.btnCalendarDay)
        if (calendarDay.date.month != currentDate.month) button.alpha = 0.5F
        button.text = calendarDay.date.dayOfMonth.toString()
        button.setOnClickListener {
            calendarDayClickListener(calendarDay)
        }
        return button
    }

    fun bindCurrentDay(calendarDay: CalendarDay, calendarDayClickListener: (calendarDay: CalendarDay) -> Unit, currentDate: ZonedDateTime, currentDayColorState: ColorStateList, textColorState: ColorStateList) {
        val button = bind(calendarDay, calendarDayClickListener, currentDate)

        button.backgroundTintList = currentDayColorState
        button.setTextColor(textColorState)
    }

    fun bindCurrentDayWithEvent(calendarDay: CalendarDay, calendarDayClickListener: (calendarDay: CalendarDay) -> Unit, currentDate: ZonedDateTime, eventCurrentDateColorState: ColorStateList) {
        val button = bind(calendarDay, calendarDayClickListener, currentDate)

        button.backgroundTintList = eventCurrentDateColorState
    }

    fun bindEventDate(calendarDay: CalendarDay, calendarDayClickListener: (calendarDay: CalendarDay) -> Unit, currentDate: ZonedDateTime, selectedColorState:ColorStateList) {
        val button = bind(calendarDay, calendarDayClickListener, currentDate)

        button.backgroundTintList = selectedColorState
    }
}