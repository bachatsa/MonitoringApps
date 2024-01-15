package com.example.ydzeinEmployee.core.domain.model

import com.example.ydzeinEmployee.core.extension.toFormattedString
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.util.Date

data class Attendance(
    var uid: String= "",
    var longitude: String = "",
    var latitude: String = "",
    val dateTime: String = LocalDateTime.now().toFormattedString("dd-MM-yyyy"),
    var type: String = "",
    var symptomsOfIllness: List<String>? = null,
    val reasonOfPermission: String? = null,
    val createAt: Timestamp = Timestamp(Date())
)
