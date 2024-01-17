package com.ydzmobile.employee.core.domain.useCase

import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun forgotPassword(email: String): Flow<ResourceState<Boolean>> = authRepositoryImpl.forgotPassword(email)
}
