package com.example.gorental.utils

import java.text.SimpleDateFormat
import java.util.Locale

    fun String.isBefore(second:String,format:String): Boolean {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date1 = dateFormat.parse(this)
        val date2 = dateFormat.parse(second)
        return date1?.before(date2) ?:false
    }