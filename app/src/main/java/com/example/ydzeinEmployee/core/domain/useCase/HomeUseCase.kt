package com.ydzmobile.employee.core.domain.useCase

import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.employee.core.domain.model.TargetListCellModel
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
){
    fun getAllTargets(): Flow<ResourceState<List<TargetModel>>> = databaseRepositoryImpl.getAllTargets()
}