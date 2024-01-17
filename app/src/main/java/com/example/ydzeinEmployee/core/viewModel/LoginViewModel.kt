package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.AuthState
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(LoginUIState())
    var uiState = _uiState.asStateFlow()

    fun loginUser() {
        useCase
            .loginUser(email = _uiState.value.email, _uiState.value.password)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(loginState = AuthState.LOGIN)
                        }
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(loginState = AuthState.FAILED, errorMessage = result.message ?: "Error")
                        }
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
        }.launchIn(viewModelScope)
    }

    fun onEmailChanged(newValue: String) {
        _uiState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue
            )
        }
    }
}

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val loginState: AuthState = AuthState.NOT_LOGIN,
)