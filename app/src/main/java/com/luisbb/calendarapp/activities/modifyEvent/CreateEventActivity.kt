package com.luisbb.calendarapp.activities.modifyEvent

import android.os.Build
import android.os.Bundle
import android.view.View
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
import com.luisbb.calendarapp.utils.epochSecondToLocalDate
import com.luisbb.calendarapp.utils.getDaysArray
import com.luisbb.calendarapp.utils.getHourArray
import com.luisbb.calendarapp.utils.getMinuteArray
import com.luisbb.calendarapp.utils.getMonthsArray
import com.luisbb.calendarapp.utils.getYearsArray
import com.luisbb.calendarapp.viewModels.activities.createEventActivity.ModifyEventViewModel
import com.luisbb.calendarapp.viewModels.activities.createEventActivity.CreateEventViewModelFactory
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class CreateEventActivity: ModifyEventActivity()