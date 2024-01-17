package com.ydzmobile.employee.core.domain.useCase

import com.google.firebase.auth.AuthResult
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun loginUser(email: String, password: String): Flow<ResourceState<AuthResult>> = authRepositoryImpl.loginUser(email, password)
    fun checkIsUserLogined(): Flow<ResourceState<Boolean>> = authRepositoryImpl.checkIsUserLogined()
}