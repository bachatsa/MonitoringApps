package com.ydzmobile.employee.core.domain.useCase

import com.ydzmobile.employee.core.data.auth.AuthRepositoryImpl
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun changePassword(newPassword: String) = authRepositoryImpl.changePassword(newPassword)
}