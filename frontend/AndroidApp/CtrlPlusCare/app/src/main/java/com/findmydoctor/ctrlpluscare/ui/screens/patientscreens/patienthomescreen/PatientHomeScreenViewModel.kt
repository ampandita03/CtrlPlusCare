package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.domain.usecases.NearbyDoctorUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientHomeScreenViewModel(
    private val nearbyDoctorUseCase: NearbyDoctorUseCase,
    private val localStorage: LocalStorage
) : ViewModel(){

    private val _uiState = MutableStateFlow<PatientHomeScreenUiState>(PatientHomeScreenUiState.Idle)
    val uiState : StateFlow<PatientHomeScreenUiState> = _uiState


    fun saveCurrentDoctor(
        doctor: Doctor
    ){
        viewModelScope.launch {
            localStorage.saveCurrentDoctor(doctor)
        }
    }
    fun getNearbyDoctors(longitude: Double,latitude : Double){
        viewModelScope.launch {
            _uiState.value = PatientHomeScreenUiState.Loading

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