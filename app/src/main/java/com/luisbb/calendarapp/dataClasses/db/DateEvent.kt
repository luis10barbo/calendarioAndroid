package com.luisbb.calendarapp.dataClasses.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_event_table")
data class DateEvent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    var id: Int,

    @ColumnInfo(name = "event_year")
    var year: Int,
    @ColumnInfo(name = "event_month")
    var month: Int,
    @ColumnInfo(name = "event_day")
    var day: Int,

    @ColumnInfo(name = "event_hour")
    var hour: Int?,
    @ColumnInfo(name = "event_minute")
    var minute: Int?,

    @ColumnInfo(name = "event_name")
    var name: String,
    @ColumnInfo(name = "event_description")
    var description: String?

)
