package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup

sealed class PatientSignUpUiState {

    object Idle : PatientSignUpUiState()
    object Loading : PatientSignUpUiState()
    object Success : PatientSignUpUiState()
    data class Error(val message: String) : PatientSignUpUiState()
}

