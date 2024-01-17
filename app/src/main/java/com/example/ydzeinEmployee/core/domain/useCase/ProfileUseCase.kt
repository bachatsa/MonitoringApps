package com.ydzmobile.employee.core.domain.useCase

import android.net.Uri
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.data.auth.AuthRepositoryImpl
import com.ydzmobile.employee.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.employee.core.data.remoteStorage.RemoteStorageImpl
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.core.domain.model.division.Division
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl,
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val storageImpl: RemoteStorageImpl
) {
    fun getCurrentUser(): Flow<ResourceState<User>> = databaseRepositoryImpl.getCurrentUser()
    fun logout(): Flow<ResourceState<Boolean>> = authRepositoryImpl.logout()
    fun updateCurrentUser(user: User): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.updateCurrentUser(user)
    fun uploadProfilePicture(uri: Uri) = storageImpl.uploadProfilePicture(uri)
    fun getPictureDownloadURL(path: String) = storageImpl.getPictureDownloadURL(path)

    fun getDivisions(): Flow<ResourceState<List<Division>>> = databaseRepositoryImpl.getDivisions()
}