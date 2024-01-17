package com.ydzmobile.employee.core.data.remoteStorage

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.ydzmobile.employee.core.data.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteStorageImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val auth: FirebaseAuth
): RemoteStorage {

    override fun uploadProfilePicture(uri: Uri): Flow<ResourceState<String>> {
        val currentUser = auth.currentUser
        val storageRef = firebaseStorage.reference
        val fileName: String = "${System.currentTimeMillis()}_${currentUser?.uid ?: "NA"}"
        return flow {
            emit(value = ResourceState.LOADING())
            val riversRef = storageRef.child("profilePictures/$fileName")
            val uploadTask = riversRef.putFile(uri)
            uploadTask.await()
            emit(value = ResourceState.SUCCESS(data = riversRef.path))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    override fun getPictureDownloadURL(path: String): Flow<ResourceState<Uri>> {
        val storageRef = firebaseStorage.reference
        return flow {
            emit(value = ResourceState.LOADING())
            val url = storageRef.child(path).downloadUrl.await()
            emit(value = ResourceState.SUCCESS(data = url))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }
}