package com.example.compose_study.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatPattern = "yyyy-MM-dd HH:mm:ss"

fun convertDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
    return dateFormat.format(date)
}

fun convertStringToDate(date: String): Date{
    val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
    return try {
        dateFormat.parse(date)
    } catch (e: Exception) {
        Date()
    }
}