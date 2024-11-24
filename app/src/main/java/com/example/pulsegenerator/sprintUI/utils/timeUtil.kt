package com.example.pulsegenerator.sprintUI.utils

import java.text.SimpleDateFormat
import java.util.Calendar

fun formatDate(milliSeconds: Long, dateFormat: String?="hh:mm:ss.SSS"): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }