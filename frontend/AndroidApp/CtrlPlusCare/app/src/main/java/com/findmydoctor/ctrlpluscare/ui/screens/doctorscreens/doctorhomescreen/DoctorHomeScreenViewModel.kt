package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorhomescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Location
import com.findmydoctor.ctrlpluscare.domain.usecases.GetDoctorPatientsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetDoctorProfileUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoctorHomeScreenViewModel(
    private val getDoctorProfileUseCase: GetDoctorProfileUseCase,
    private val getDoctorPatientsUseCase: GetDoctorPatientsUseCase,
    private val localStorage: LocalStorage
) : ViewModel() {

    companion object {
        private const val TAG = "DoctorHomeVM"
    }

    private val _profile =
        MutableStateFlow<DoctorProfileUiState>(DoctorProfileUiState.Idle)
    val profile: StateFlow<DoctorProfileUiState> = _profile

    private val _uiState =
        MutableStateFlow<DoctorHomeScreenUiState>(DoctorHomeScreenUiState.Idle)
    val uiState: StateFlow<DoctorHomeScreenUiState> = _uiState

    private val _location =
        MutableStateFlow(Location(latitude = 0.0, longitude = 0.0))
    val location: StateFlow<Location> = _location

    init {
        Log.d(TAG, "DoctorHomeScreenViewModel initialized")
    }

    fun getDoctorProfile() {
        Log.d(TAG, "getDoctorProfile() called")

        viewModelScope.launch {
            _profile.value = DoctorProfileUiState.Loading
            Log.d(TAG, "Doctor profile loading started")

            val result = getDoctorProfileUseCase()

            result.onSuccess { response ->
                Log.d(TAG, "Doctor profile fetched successfully")
                _profile.value = DoctorProfileUiState.DoctorProfileLoaded(response)

                localStorage.saveDoctorProfile(response.data)
                Log.d(TAG, "Doctor profile saved to local storage")
            }

            result.onFailure { error ->
                Log.e(TAG, "Failed to fetch doctor profile: ${error.message}", error)
                _profile.value =
                    DoctorProfileUiState.Error(error.message ?: "Unknown error")
            }
        }
    }

    fun getPatients() {
        Log.d(TAG, "getPatients() called")

        viewModelScope.launch {
            _uiState.value = DoctorHomeScreenUiState.Loading
            Log.d(TAG, "Fetching doctor patients started")

            val result = getDoctorPatientsUseCase()

            result.onSuccess { response ->
                Log.d(TAG, "Doctor patients fetched successfully")
                _uiState.value = DoctorHomeScreenUiState.Success(response)
            }

            result.onFailure { error ->
                Log.e(TAG, "Failed to fetch doctor patients: ${error.message}", error)
                _uiState.value =
                    DoctorHomeScreenUiState.Error(error.message ?: "Unknown error")
            }
        }
    }

    fun saveUserLocation(latitude: Double, longitude: Double) {
        Log.d(
            TAG,
            "saveUserLocation() called with lat=$latitude, lng=$longitude"
        )

        viewModelScope.launch {
            val location = Location(latitude, longitude)
            _location.value = location
            localStorage.saveCurrentLocation(location)

            Log.d(TAG, "Doctor location saved successfully: $location")
        }
    }
}
