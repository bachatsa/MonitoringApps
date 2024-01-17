package com.ydzmobile.employee.core.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.employee.core.data.AuthState
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.core.domain.useCase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(ProfileUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getCurrentUser()
        getDivisions()
    }

    fun getCurrentUser() {
        useCase.getCurrentUser()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        Log.d("ADADA", result.data.toString())
                        _uiState.update {
                            it.copy(user = result.data!!)
                        }
                        getPictureDownloadURL()
                        Log.d("getCurrentUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("getCurrentUser", result.message ?: "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("getCurrentUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun logout() {
        useCase
            .logout()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isLoggedOut = true)
                        }


                        Log.d("logout", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("logout", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("logout", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun onNameChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    name = newValue
                )
            )
        }
    }

    fun onBirthDateChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    dateBirth = newValue
                )
            )
        }
    }

    fun onPhoneNumberChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    phoneNumber = newValue
                )
            )
        }
    }

    fun onAddressChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    address = newValue
                )
            )
        }
    }

    fun onRtChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    rt = newValue
                )
            )
        }
    }

    fun onDivisionChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    division = newValue
                )
            )
        }
    }

    fun onBloodTypeChanged(newValue: String) {
        _uiState.update {
            it.copy(
                user = it.user.copy(
                    bloodType = newValue
                )
            )
        }
    }

    fun onSelectedImage(newValue: Uri?) {
        _uiState.update {
            it.copy(
                selectedImages = newValue
            )
        }
    }

    fun updateProfile() {
        if (_uiState.value.selectedImages != null) {
            uploadProfilePictures(_uiState.value.selectedImages!!)
        } else {
            updateUserDetail()
        }
    }

    private fun updateUserDetail() {
        val user = _uiState.value.user
        useCase
            .updateCurrentUser(user)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                isUpdated = true
                            )
                        }
                        Log.d("updateCurrentUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("updateCurrentUser", result.message ?: "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("updateCurrentUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun uploadProfilePictures(uri: Uri) {
        useCase
            .uploadProfilePicture(uri)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                user = it.user.copy(
                                    profilePicture = result.data!!
                                )
                            )
                        }
                        updateUserDetail()
                        Log.d("uploadProfilePictures", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("uploadProfilePictures", result.message ?: "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("uploadProfilePictures", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getPictureDownloadURL() {
        useCase
            .getPictureDownloadURL(_uiState.value.user.profilePicture ?: "")
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                downloadedProfileImage = result.data
                            )
                        }
                        Log.d("getPictureDownloadURL", "SUCCESS ${result.data.toString()}")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("getPictureDownloadURL", result.message ?: "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("getPictureDownloadURL", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getDivisions() {
        useCase.getDivisions().onEach { result ->
            when (result) {
                is ResourceState.SUCCESS -> {
                    _uiState.update {
                        it.copy(
                            divisions = result.data!!.map { division ->  division.name }
                        )
                    }
                    Log.d("getDivisions", "SUCCESS")
                }

                is ResourceState.ERROR -> {
                    Log.d("getDivisions", "ERROR")
                }

                is ResourceState.LOADING -> {
                    Log.d("getDivisions", "LOADING")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}

data class ProfileUIState(
    val user: User = User(),
    val bloodTypes: List<String> = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"),
    val divisions: List<String> = listOf(),
    val selectedImages: Uri? = null,
    val downloadedProfileImage: Uri? = null,
    val isLoggedOut: Boolean = false,
    val isUpdated: Boolean = false
)