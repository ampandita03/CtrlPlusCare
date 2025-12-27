package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorhomescreen

import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientAppointmentResponse

sealed class DoctorProfileUiState {

    object Idle : DoctorProfileUiState()
    object Loading : DoctorProfileUiState()
    data class DoctorProfileLoaded(val doctorProfileResponse: DoctorProfileResponse) : DoctorProfileUiState()
    data class Error(val message:String): DoctorProfileUiState()
}

sealed class DoctorHomeScreenUiState {

    object Idle : DoctorHomeScreenUiState()
    object Loading : DoctorHomeScreenUiState()
    data class Success(val data : PatientAppointmentResponse) : DoctorHomeScreenUiState()
    data class Error(val message:String): DoctorHomeScreenUiState()
}