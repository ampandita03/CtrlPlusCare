package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen

import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse

sealed class PatientHomeScreenUiState {
    object Idle : PatientHomeScreenUiState()
    object Loading : PatientHomeScreenUiState()
    data class NearByDoctorsLoaded(val doctors: DoctorResponse) : PatientHomeScreenUiState()
    data class Error(val message:String): PatientHomeScreenUiState()
}