package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.employee.core.domain.useCase.AttendanceHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AttendanceHistoryViewModel @Inject constructor(
    private val useCase: AttendanceHistoryUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(AttendanceHistoryUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getHistories()
    }
    fun getHistories() {
        useCase.getHistories().onEach { result ->
            when (result) {
                is ResourceState.SUCCESS -> {
                    _uiState.update {
                        it.copy(
                            histories = result.data!!
                        )
                    }
                    Log.d("getOrder", "SUCCESS")
                }

                is ResourceState.ERROR -> {
                    Log.d("getOrder", "ERROR")
                }

                is ResourceState.LOADING -> {
                    Log.d("getOrder", "LOADING")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}

data class AttendanceHistoryUIState (
    val histories: List<AttendanceHistoryModel> = listOf()
)