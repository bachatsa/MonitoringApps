package com.ydzmobile.employee.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.maps.android.ktx.utils.sphericalDistance
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.attendance.Attendance
import com.ydzmobile.employee.core.domain.useCase.PermitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PermitViewModel @Inject constructor(
    private val useCase: PermitUseCase
) : ViewModel() {
    fun onPermitPressed(reason: String) {
        val attendance = Attendance(type = "IZIN", reasonOfPermission = reason)
        doAttendance(attendance)
    }

    fun onSickSubmitPressed(symptomsOfIllness: List<String>, reason: String) {
        val attendance = Attendance(type = "SAKIT", symptomsOfIllness = symptomsOfIllness, reasonOfPermission = reason)
        doAttendance(attendance)
    }

    private fun doAttendance(attendance: Attendance) {
        useCase
            .doAttendance(attendance).onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {

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