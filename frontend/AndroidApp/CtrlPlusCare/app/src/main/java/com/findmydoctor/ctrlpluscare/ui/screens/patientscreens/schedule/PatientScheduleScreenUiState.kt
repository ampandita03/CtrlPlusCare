package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.schedule

import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse

sealed class PatientScheduleScreenUiState {

    object Idle : PatientScheduleScreenUiState()
    object Loading : PatientScheduleScreenUiState()
    data class Success(val data : AppointmentsResponse) : PatientScheduleScreenUiState()
    data class Error(val message : String) : PatientScheduleScreenUiState()
}

sealed class CancelAppointmentUiState {

    object Loading : CancelAppointmentUiState()
    object Success: CancelAppointmentUiState()
    data class Error(val message : String) : CancelAppointmentUiState()
}

