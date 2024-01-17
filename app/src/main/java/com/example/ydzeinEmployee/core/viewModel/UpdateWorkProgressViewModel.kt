package com.ydzmobile.employee.core.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.domain.model.monitor.TargetModelRequest
import com.ydzmobile.employee.core.domain.useCase.TargetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UpdateWorkProgressViewModel @Inject constructor(
    private val useCase: TargetUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(UpdateWorkProgressUIState())
    var uiState = _uiState.asStateFlow()

    init {
        checkIsHasDoAttendance()
    }

    fun setupUsingModel(target: TargetModel) {
        _uiState.update {
            it.copy(
                target = target,
                isFinishedTarget = (_uiState.value.target?.targetBeenDone
                    ?: 0) == (_uiState.value.target?.totalTarget ?: 0)
            )
        }

        checkForm()
    }


    fun onTargetBeenDone(newValue: String) {
        _uiState.update {
            it.copy(
                targetBeenDone = newValue,
            )
        }

        checkForm()
    }


    fun onSelectedImage(newValue: Uri?) {
        _uiState.update {
            it.copy(
                selectedImage = newValue,
            )
        }

        checkForm()
    }

    fun updateTarget() {

        if (_uiState.value.targetBeenDone.isNotEmpty()){
            _uiState.update {
                it.copy(
                    target = it.target!!.copy(
                        targetBeenDone = _uiState.value.target?.targetBeenDone!! + _uiState.value.targetBeenDone.toInt()
                    )
                )
            }
        }

        val target = TargetModelRequest(
            idEmployee = _uiState.value.target?.idEmployee ?: "",
            idDivision = _uiState.value.target?.idDivision ?: "",
            totalTarget = (_uiState.value.target?.totalTarget) ?: "0".toInt(),
            productType = _uiState.value.target?.productType ?: "",
            dateStart = _uiState.value.target?.dateStart ?: "",
            dateFinish = _uiState.value.target?.dateFinish ?: "",
            targetType = _uiState.value.target?.targetType ?: "",
            targetBeenDone = (_uiState.value.target?.targetBeenDone) ?: "0".toInt()
        )
        useCase
             .updateTarget(target = target, idTarget = _uiState.value.target?.id ?: "", _uiState.value.selectedImage!!)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(isSuccess = false)
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
    fun checkIsHasDoAttendance() {
        useCase
             .checkIsHasDoAttendance()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isHasDoAttendance = result.data!!, isLoaded = true)
                        }
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun checkForm() {
        _uiState.update {
            it.copy(
                isValid = _uiState.value.selectedImage != null && _uiState.value.targetBeenDone.isNotEmpty()
            )
        }
    }
}

data class UpdateWorkProgressUIState(
    val target: TargetModel? = null,
    val selectedImage: Uri? = null,
    val targetBeenDone: String = "0",
    val isSuccess: Boolean = false,
    val isValid: Boolean = false,
    val isFinishedTarget: Boolean = false,
    val isHasDoAttendance: Boolean = false,
    val isLoaded: Boolean = false,
)