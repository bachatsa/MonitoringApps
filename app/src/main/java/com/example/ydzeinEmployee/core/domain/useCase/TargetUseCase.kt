package com.ydzmobile.employee.core.domain.useCase

import android.net.Uri
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.domain.model.monitor.TargetModelRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TargetUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun getMyTargets(targetType: String): Flow<ResourceState<List<TargetModel>>> = databaseRepositoryImpl.getMyTargets(targetType)

    fun updateTarget(
        target: TargetModelRequest,
        idTarget: String,
        uri: Uri
    ): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.updateTarget(target, idTarget, uri)

    fun checkIsHasDoAttendance(): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.checkIsHasDoAttendance()
}