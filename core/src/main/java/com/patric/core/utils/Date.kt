package com.patric.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateNowStart(): String {
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)
    val dateFormat = SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(now.time)
}

fun dateNowEnd(): String {
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 23)
    now.set(Calendar.MINUTE, 59)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)
    val dateFormat = SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(now.time)
}


fun dateMorningStart(): String {
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)
    now.add(Calendar.DATE, 1)
    val dateFormat = SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(now.time)
}

fun dateMorningEnd(): String {
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 23)
    now.set(Calendar.MINUTE, 59)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)
    now.add(Calendar.DATE, 1)
    val dateFormat = SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(now.time)
}