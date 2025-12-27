package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.domain.usecases.NearbyDoctorUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmergencyScreenViewModel(
    private val nearbyDoctorUseCase: NearbyDoctorUseCase,
    private val localStorage: LocalStorage
    ): ViewModel() {
    private val _uiState = MutableStateFlow<PatientHomeScreenUiState>(PatientHomeScreenUiState.Idle)
    val uiState : StateFlow<PatientHomeScreenUiState> = _uiState

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
    fun saveCurrentDoctor(
        doctor: Doctor
    ){
        viewModelScope.launch {
            localStorage.saveCurrentDoctor(doctor)
        }
    }
}