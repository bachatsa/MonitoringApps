package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.useCase.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val useCase: ForgotPasswordUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(ForgotPasswordUIState())
    var uiState = _uiState.asStateFlow()

    fun onEmailChanged(newValue: String) {
        _uiState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun onSubmitPressed() {
        useCase
            .forgotPassword(_uiState.value.email)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                        Log.d("forgotPassword", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(isSuccess = false)
                        }
                        Log.d("forgotPassword", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("forgotPassword", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
}

data class ForgotPasswordUIState(
    val email: String = "",
    val isSuccess: Boolean = false
)