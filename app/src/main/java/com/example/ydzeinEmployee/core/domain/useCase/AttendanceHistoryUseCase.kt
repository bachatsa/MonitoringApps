package com.ydzmobile.employee.core.domain.useCase

import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.employee.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.employee.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceHistoryUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
){

    fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>> = databaseRepositoryImpl.getHistories()
}