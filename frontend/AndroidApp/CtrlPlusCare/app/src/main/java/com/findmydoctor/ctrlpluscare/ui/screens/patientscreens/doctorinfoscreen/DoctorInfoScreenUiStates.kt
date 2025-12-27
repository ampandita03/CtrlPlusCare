package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen

import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse

sealed class DoctorInfoScreenUiStates {
    object Idle : DoctorInfoScreenUiStates()
    object Loading : DoctorInfoScreenUiStates()
    data class Success(val data : TimeSlotsResponse) : DoctorInfoScreenUiStates()
    data class Error(val message: String) : DoctorInfoScreenUiStates()
}

sealed class CurrentDoctorUiState{
    object Loading : CurrentDoctorUiState()
    data class Success(val doctor: Doctor ) : CurrentDoctorUiState()
    data class Error(val message: String) : CurrentDoctorUiState()

}