package com.ydzmobile.employee.core.data.auth

import com.google.firebase.auth.AuthResult
import com.ydzmobile.employee.core.data.ResourceState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(email: String, password: String): Flow<ResourceState<AuthResult>>

    fun registerUser(email: String, password: String): Flow<ResourceState<AuthResult>>

    fun changePassword(newPassword: String): Flow<ResourceState<Boolean>>
    fun forgotPassword(email: String): Flow<ResourceState<Boolean>>

    fun checkIsUserLogined(): Flow<ResourceState<Boolean>>
    fun logout(): Flow<ResourceState<Boolean>>
}