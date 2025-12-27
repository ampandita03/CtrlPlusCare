package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientdiscoverypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.domain.usecases.AllDoctorUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientDiscoveryViewModel(
    private val localStorage: LocalStorage,
    private val getAllDoctorUseCase: AllDoctorUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<PatientHomeScreenUiState>(PatientHomeScreenUiState.Idle)
    val uiState : StateFlow<PatientHomeScreenUiState> = _uiState


    fun getAllDoctors(){
        viewModelScope.launch {
            _uiState.value = PatientHomeScreenUiState.Loading

            val result = getAllDoctorUseCase()
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