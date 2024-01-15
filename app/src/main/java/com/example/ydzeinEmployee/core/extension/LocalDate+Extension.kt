package com.example.ydzeinEmployee.core.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFormattedString(pattern: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}