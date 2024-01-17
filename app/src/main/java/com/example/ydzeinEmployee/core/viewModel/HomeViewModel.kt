package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.domain.useCase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(HomeUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getAllTargets()
    }

    fun getAllTargets() {
        useCase
            .getAllTargets()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                targets = result.data!!,
                                totalItemTargets = result.data.sumOf { element -> element.totalTarget },
                                totalItemDone = result.data.sumOf { element -> element.targetBeenDone },
                            )
                        }
                        Log.d("getTargets", "SUCCESS ${result.data!!}")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("getTargets", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("getTargets", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

}

data class  HomeUIState(
    val targets: List<TargetModel> = listOf(),
    val totalItemDone: Int = 0,
    val totalItemTargets: Int = 0,
)