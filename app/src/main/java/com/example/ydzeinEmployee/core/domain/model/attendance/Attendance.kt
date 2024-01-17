package com.ydzmobile.employee.core.domain.model.attendance

import com.google.firebase.Timestamp
import com.ydzmobile.employee.core.extension.toFormattedString
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
    