package com.example.ydzeinEmployee.core.data.database

import android.net.Uri
import com.example.ydzeinEmployee.core.data.ResourceState
import com.example.ydzeinEmployee.core.domain.model.Attendance
import com.example.ydzeinEmployee.core.domain.model.AttendanceHistoryModel
import com.example.ydzeinEmployee.core.domain.model.Division
import com.example.ydzeinEmployee.core.domain.model.auth.TargetModel
import com.example.ydzeinEmployee.core.domain.model.auth.TargetModelRequest
import com.google.firebase.firestore.auth.User
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