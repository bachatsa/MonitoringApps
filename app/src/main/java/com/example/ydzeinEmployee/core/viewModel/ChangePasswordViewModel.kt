package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.useCase.ChangePasswordUseCase
import com.ydzmobile.employee.core.extension.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val useCase: ChangePasswordUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(ChangePasswordUIState())
    var uiState = _uiState.asStateFlow()

    fun onPasswordChanged(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue,
                passwordError = !newValue.isValidPassword(),
            )
        }
        isValidForm()
    }

    fun onPasswordConfirmChanged(newValue: String) {
        _uiState.update {
            it.copy(
                passwordConfirm = newValue,
                passwordConfirmError = _uiState.value.password != newValue,
            )
        }
        isValidForm()
    }

    private fun isValidForm() {
        _uiState.update {
            it.copy(
                isValidForm = !(_uiState.value.passwordError || _uiState.value.passwordConfirmError)
            )
        }
    }

    fun changePassword() {
        useCase.changePassword(_uiState.value.password)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                        Log.d("changePassword", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("changePassword", result.message ?: "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("changePassword", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
}

data class ChangePasswordUIState(
    val password: String = "",
    val passwordError: Boolean = false,
    val passwordConfirm: String = "",
    val passwordConfirmError: Boolean = false,
    val isValidForm: Boolean = false,
    val isSuccess: Boolean = false
)