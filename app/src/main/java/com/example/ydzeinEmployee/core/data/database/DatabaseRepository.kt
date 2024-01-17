package com.ydzmobile.employee.core.data.database

import android.net.Uri
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.employee.core.domain.model.attendance.Attendance
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.core.domain.model.division.Division
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.domain.model.monitor.TargetModelRequest
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun createUser(user: User): Flow<ResourceState<Boolean>>

    fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>>

    fun getCurrentUser(): Flow<ResourceState<User>>
    fun updateCurrentUser(user: User): Flow<ResourceState<Boolean>>

    fun getDivisions(): Flow<ResourceState<List<Division>>>
    fun getMyTargets(targetType: String): Flow<ResourceState<List<TargetModel>>>

    fun getAllTargets(): Flow<ResourceState<List<TargetModel>>>
    fun createTargets(division: String, target: TargetModelRequest): Flow<ResourceState<Boolean>>
    fun deleteTarget(target: TargetModelRequest, idTarget: String): Flow<ResourceState<Boolean>>
    fun updateTarget(target: TargetModelRequest, idTarget: String, uri: Uri): Flow<ResourceState<Boolean>>

    fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>>

    fun checkIsAbleToDoAttendance(): Flow<ResourceState<Boolean>>
    fun checkIsHasDoAttendance(): Flow<ResourceState<Boolean>>
}