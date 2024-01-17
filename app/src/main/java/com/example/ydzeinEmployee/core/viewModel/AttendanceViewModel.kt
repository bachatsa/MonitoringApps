package com.ydzmobile.employee.core.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.utils.sphericalDistance
import com.ydzmobile.employee.core.data.AuthState
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.attendance.Attendance
import com.ydzmobile.employee.core.domain.useCase.AttendanceUseCase
import com.ydzmobile.employee.core.domain.useCase.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val attendanceUseCase: AttendanceUseCase
): ViewModel() {
    private val _locationState: MutableStateFlow<LocationState> = MutableStateFlow(LocationState.Loading)
    val locationState = _locationState.asStateFlow()

    private var _uiState = MutableStateFlow(AttendanceUIState())
    var uiState = _uiState.asStateFlow()

    init {
        checkIsAbleToDoAttendance()
    }

    
    fun handle(event: PermissionEvent) {
        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUseCase.invoke().collect { location ->
                        _locationState.value = LocationState.Success(location)
                        _uiState.update {
                            it.copy(
                                currentLocation = LatLng(
                                    location?.latitude ?: 0.0,
                                    location?.longitude ?: 0.0
                                ),
                                isAbleToDoAttendance = if(_uiState.value.currentLocation != null) {
                                    LatLng(
                                        location?.latitude ?: 0.0,
                                        location?.longitude ?: 0.0
                                    ).sphericalDistance(_uiState.value.epic)  <= 50
                                } else {
                                    false
                                }

                            )
                        }
                    }
                }
            }

            PermissionEvent.Revoked -> {
                _locationState.value = LocationState.RevokedPermissions
            }

            else -> {}
        }
    }

    fun onPresentPressed() {
        val distance = _uiState.value.currentLocation?.sphericalDistance(_uiState.value.epic) ?: 0.0
        if (distance <= 20) {
            val attendance = Attendance(longitude = _uiState.value.currentLocation!!.longitude.toString(), latitude = _uiState.value.currentLocation!!.latitude.toString(), type = "MASUK")
            attendanceUseCase
                .doAttendance(attendance).onEach { result ->
                    when (result) {
                        is ResourceState.SUCCESS -> {
                            _uiState.update {
                                it.copy(
                                    isSuccess = true,
                                    toastMessages = "Sukses melakukan Absensi"
                                )
                            }
                            Log.d("onPresentPressed", "SUCCESS")
                        }

                        is ResourceState.ERROR -> {

                            Log.d("onPresentPressed", "ERROR")
                        }

                        is ResourceState.LOADING -> {
                            Log.d("onPresentPressed", "LOADING")
                        }

                        else -> {}
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun checkIsAbleToDoAttendance() {
        attendanceUseCase
            .checkIsAbleToDoAttendance()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {

                        Log.d("checkIsAbleToDoAttendance", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(
                                isSuccess = true,
                                toastMessages = result.message!!
                            )
                        }
                        Log.d("checkIsAbleToDoAttendance", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("checkIsAbleToDoAttendance", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun clearToast() {
        _uiState.update {
            it.copy(
                toastMessages = ""
            )
        }
    }
}

sealed interface LocationState {
    object Loading : LocationState
    data class Success(val location: LatLng?) : LocationState
    object RevokedPermissions : LocationState
}

sealed interface PermissionEvent {
    object Granted : PermissionEvent
    object Revoked : PermissionEvent
}

data class AttendanceUIState(
    var currentLocation: LatLng? = null,
    var epic: LatLng = LatLng(-6.2068924, 107.0079269),
    val toastMessages: String = "",
    val isSuccess: Boolean = false,
    val isAbleToDoAttendance: Boolean = false
)