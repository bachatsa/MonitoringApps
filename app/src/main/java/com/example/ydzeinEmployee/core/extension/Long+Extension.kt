package com.ydzmobile.employee.core.extension

import java.text.SimpleDateFormat
import java.util.Date

fun Long.longToDateStr(format: String = "dd-MM-yyyy"): String {
    val dateFormat = SimpleDateFormat(format)
    val date = Date(this)
    return dateFormat.format(date)
}