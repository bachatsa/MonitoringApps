package com.ydzmobile.employee.core.domain.model.attendanceMonitor

data class AttendanceMonitorCellModel(
    val uid: String,
    val id: String,
    var userName: String,
    var division: String,
    val attendanceType: String,
    val date: String,
)
