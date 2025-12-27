package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen

import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingResponse
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse

sealed class EmergencyDoctorInfoUiState {
    object Idle : EmergencyDoctorInfoUiState()
    object Loading : EmergencyDoctorInfoUiState()
    data class Success(val data : EmergencyBookingResponse) : EmergencyDoctorInfoUiState()
    data class Error(val message: String) : EmergencyDoctorInfoUiState()
}
