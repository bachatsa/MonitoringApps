package com.ydzmobile.employee.core.extension

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toDate(pattern: String = "yyyy-MM-dd"): LocalDateTime? {
    try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(this, formatter)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun String.isValidPassword(): Boolean {
    return this.count() >= 8
}

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")  // Simple email regex, adjust as needed
    return emailRegex.matches(this)
}

fun String.dateStringToLong(format: String = "dd-MM-yyyy"): Long {
    if (this.isNotEmpty()){
        val dateFormat = SimpleDateFormat(format)
        val date = dateFormat.parse(this)
        return date?.time ?: 0L
    } else {
        return 0L
    }
}