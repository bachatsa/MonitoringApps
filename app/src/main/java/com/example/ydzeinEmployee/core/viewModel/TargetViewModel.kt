package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.domain.useCase.TargetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TargetViewModel @Inject constructor(
    private val useCase: TargetUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(TargetUIState())
    var uiState = _uiState.asStateFlow()
    fun getMyTargets() {
        useCase.getMyTargets(_uiState.value.targetTypeText).onEach { result ->
            when (result) {
                is ResourceState.SUCCESS -> {
                    _uiState.update {
                        it.copy(
                            targets = result.data!!,
                        )
                    }
                    Log.d("getMyTargets", "SUCCESS")
                }

                is ResourceState.ERROR -> {
                    Log.d("getMyTargets", "ERROR")
                }

                is ResourceState.LOADING -> {
                    Log.d("getMyTargets", "LOADING")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun setTargetName(name: String) {
        _uiState.update {
            it.copy(
                targetTypeText = name
            )
        }

        getMyTargets()
    }
}

data class TargetUIState(
    val targetTypeText: String = "",
    val targetType: List<String> = listOf("Primer", "Sekunder"),
    val targets: List<TargetModel> = listOf(),
)