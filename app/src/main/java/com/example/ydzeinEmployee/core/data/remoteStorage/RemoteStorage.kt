package com.ydzmobile.employee.core.data.remoteStorage

import android.net.Uri
import com.ydzmobile.employee.core.data.ResourceState
import kotlinx.coroutines.flow.Flow

interface RemoteStorage {
    fun uploadProfilePicture(uri: Uri): Flow<ResourceState<String>>

    fun getPictureDownloadURL(path: String): Flow<ResourceState<Uri>>
}