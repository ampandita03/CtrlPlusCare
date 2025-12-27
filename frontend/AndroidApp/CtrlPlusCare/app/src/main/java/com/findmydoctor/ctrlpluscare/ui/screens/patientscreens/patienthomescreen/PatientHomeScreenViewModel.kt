package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.data.dto.Location
import com.findmydoctor.ctrlpluscare.domain.usecases.NearbyDoctorUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientProfileUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientHomeScreenViewModel(
    private val nearbyDoctorUseCase: NearbyDoctorUseCase,
    private val patientProfileUseCase: PatientProfileUseCase,
    private val localStorage: LocalStorage
) : ViewModel(){

    private val _uiState = MutableStateFlow<PatientHomeScreenUiState>(PatientHomeScreenUiState.Idle)
    val uiState : StateFlow<PatientHomeScreenUiState> = _uiState


    private val _profile = MutableStateFlow<PatientProfileUiState>(PatientProfileUiState.Idle)
    val profile : StateFlow<PatientProfileUiState> = _profile
    private val _location = MutableStateFlow(Location(latitude = 0.0, longitude = 0.0))
    val location: StateFlow<Location> = _location

    fun saveCurrentDoctor(
        doctor: Doctor
    ){
        viewModelScope.launch {
            localStorage.saveCurrentDoctor(doctor)
        }
    }

    fun getPatientProfile() {
        viewModelScope.launch {
            _profile.value = PatientProfileUiState.Loading
            val result = patientProfileUseCase()
            result.onSuccess {
                _profile.value = PatientProfileUiState.PatientProfileLoaded(it)
                localStorage.savePatientProfile(it)
            }
            result.onFailure {
                _profile.value = PatientProfileUiState.Error(it.message.toString())
            }
        }
    }
    fun saveUserLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _location.value = Location(latitude, longitude)
            localStorage.saveCurrentLocation(Location(latitude,longitude))
        }
    }

    fun getNearbyDoctors(){
        viewModelScope.launch {
            _uiState.value = PatientHomeScreenUiState.Loading
            val latitude = localStorage.getCurrentLocation()?.latitude?:0.0

            val longitude = localStorage.getCurrentLocation()?.longitude?:0.0

            val result = nearbyDoctorUseCase(longitude, latitude)
            result.onSuccess {
                _uiState.value = PatientHomeScreenUiState.NearByDoctorsLoaded(it)
            }
            result.onFailure {
                _uiState.value = PatientHomeScreenUiState.Error(it.message.toString())
            }
        }
    }
}