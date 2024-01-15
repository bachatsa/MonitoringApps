package com.example.ydzeinEmployee.core.data.auth

import com.example.ydzeinEmployee.core.data.ResourceState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImple @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<ResourceState<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun registerUser(email: String, password: String): Flow<ResourceState<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun changePassword(newPassword: String): Flow<ResourceState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun forgotPassword(email: String): Flow<ResourceState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun checkIsUserLogined(): Flow<ResourceState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun logout(): Flow<ResourceState<Boolean>> {
        TODO("Not yet implemented")
    }

}