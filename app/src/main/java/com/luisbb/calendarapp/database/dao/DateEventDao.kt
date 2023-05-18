package com.luisbb.calendarapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luisbb.calendarapp.dataClasses.db.DateEvent
import java.time.LocalDate

@Dao
interface DateEventDao {
    @Insert
    suspend fun insertDateEvent(dateEvent: DateEvent)

    @Update
    suspend fun updateDateEvent(dateEvent: DateEvent)

    @Delete
    suspend fun deleteDateEvent(dateEvent: DateEvent)

    @Query("SELECT * FROM date_event_table")
    fun getAllDateEvents(): LiveData<List<DateEvent>>

    @Query("SELECT * FROM date_event_table WHERE event_year = :year AND event_month = :month ORDER BY event_day ASC")
    fun getMonthEvents(year: Int, month: Int): LiveData<List<DateEvent>>
    @Query("SELECT * FROM date_event_table WHERE event_year = :year AND event_month = :month AND event_day >= :day  ORDER BY event_day ASC ")
    fun getMonthUpcomingEvents(year: Int, month: Int, day:Int): LiveData<List<DateEvent>>

    @Query("SELECT * FROM date_event_table WHERE event_day = :day AND event_month = :month AND event_year = :year ORDER BY event_day ASC, event_month ASC, event_year ASC")
    fun getDateEvents(year: Int, month: Int, day: Int): LiveData<List<DateEvent>>

    @Query("SELECT * FROM date_event_table WHERE event_id = :eventId")
    fun getDateEvent(eventId: Int): LiveData<DateEvent>
}