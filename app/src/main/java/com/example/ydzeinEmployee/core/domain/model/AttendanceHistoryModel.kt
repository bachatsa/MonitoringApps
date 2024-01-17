package com.ydzmobile.employee.core.domain.model

import androidx.compose.ui.graphics.Color
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.enum.AttendanceType
import com.ydzmobile.employee.core.extension.toDate
import com.ydzmobile.employee.core.extension.toFormattedString
import com.ydzmobile.employee.ui.theme.appleGreen
import com.ydzmobile.employee.ui.theme.lightCarminePink
import java.time.LocalDateTime


data class AttendanceHistoryModel(
    val id: String,
    var date: String = "",
    var dateStart: String,
    var dateFinish: String,
    val attendanceType: AttendanceType,
    val symptomsOfIllness: List<String> = listOf(),
    val reasonOfPermission: String = ""
) {
    fun getBackgroundColor(): Color {
        return when(attendanceType) {
            AttendanceType.PRESENT -> appleGreen
            else -> lightCarminePink
        }
    }

    fun getTitle(): String {
        val dateLocaleDate: LocalDateTime = dateStart.toDate("yyyy-MM-dd HH:mm:ss") ?: return ""
        return dateLocaleDate.toFormattedString("dd-MM-yyyy")
    }

    fun getSubTitle(): String {
        return when (attendanceType) {
            AttendanceType.SICK -> "Sakit"
            AttendanceType.PRESENT -> "Masuk"
            AttendanceType.PERMIT -> "Izin"
        }
    }

    fun getDescription(): String {
        return when (attendanceType) {
            AttendanceType.SICK -> {
                symptomsOfIllness.joinToString(separator = ", ")
            }
            AttendanceType.PRESENT -> {
                val start: String = dateStart.toDate("yyyy-MM-dd HH:mm:ss")?.toFormattedString("HH:mm") ?: "..."
                val end: String = dateFinish.toDate("yyyy-MM-dd HH:mm:ss")?.toFormattedString("HH:mm") ?: "..."
                return "$start - $end"
            }
            AttendanceType.PERMIT -> reasonOfPermission
        }
    }

    fun getIcon(): Int {
        return when (attendanceType) {
            AttendanceType.SICK -> R.drawable.ic_sick
            AttendanceType.PRESENT -> R.drawable.ic_present
            AttendanceType.PERMIT -> R.drawable.ic_permit
        }
    }
}