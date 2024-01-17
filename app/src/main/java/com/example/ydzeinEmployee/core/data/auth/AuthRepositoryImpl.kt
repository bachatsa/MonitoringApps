package com.ydzmobile.employee.core.data.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<ResourceState<AuthResult>> {
        val dbUsers = db.collection("Users")
        return flow {
            emit(value = ResourceState.LOADING())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = dbUsers.document(firebaseAuth.uid ?: "").get().await().toObject(User::class.java)!!

            if (user.role == "EMPLOYEE") {
                emit(value = ResourceState.SUCCESS(data = result))
            } else {
                firebaseAuth.signOut()
                emit(value = ResourceState.ERROR("Akun anda tidak terdaftar sebagai pegawai"))
            }
        }.catch {
            emit(value = ResourceState.ERROR("Tolong Periksa Kembali Email & Password Anda"))
        }
    }

    override fun registerUser(email: String, password: String): Flow<ResourceState<AuthResult>> {
        return flow {
            emit(value = ResourceState.LOADING())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(value = ResourceState.SUCCESS(data = result))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    override fun changePassword(newPassword: String): Flow<ResourceState<Boolean>> {
        val currentUser = firebaseAuth.currentUser!!
        return flow {
            emit(value = ResourceState.LOADING())
            currentUser.updatePassword(newPassword).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    override fun forgotPassword(email: String): Flow<ResourceState<Boolean>> {
        return flow {
            emit(value = ResourceState.LOADING())
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    override fun checkIsUserLogined(): Flow<ResourceState<Boolean>> {
        return flow {
            emit(value = ResourceState.LOADING())

            if (firebaseAuth.currentUser != null){
                emit(value = ResourceState.SUCCESS(data = true))
            } else {
                emit(value = ResourceState.SUCCESS(data = false))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    override fun logout(): Flow<ResourceState<Boolean>> {
        return flow {
            emit(value = ResourceState.LOADING())
            firebaseAuth.signOut()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }
}