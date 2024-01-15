package com.example.ydzeinEmployee.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(this) ?: ""
}