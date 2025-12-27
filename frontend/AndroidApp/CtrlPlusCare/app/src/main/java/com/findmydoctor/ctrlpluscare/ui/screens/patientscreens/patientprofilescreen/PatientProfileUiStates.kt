package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen

import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse

sealed class PatientProfileUiState{
    object Idle : PatientProfileUiState()

    object Loading : PatientProfileUiState()

    data class PatientProfileLoaded(val patientProfileResponse: PatientProfileResponse) : PatientProfileUiState()
    data class Error(val message:String): PatientProfileUiState()
}
sealed class UpdatePatientProfileUiState{
    object Idle : UpdatePatientProfileUiState()

    object Loading : UpdatePatientProfileUiState()

    object Success : UpdatePatientProfileUiState()

    data class Error(val message:String): UpdatePatientProfileUiState()
}
