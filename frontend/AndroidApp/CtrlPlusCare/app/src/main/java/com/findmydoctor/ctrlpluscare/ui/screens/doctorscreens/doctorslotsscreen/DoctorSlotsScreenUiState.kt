package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorslotsscreen

import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfile
import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse

sealed class GetSlotsScreenUiState {
    object Idle : GetSlotsScreenUiState()
    object Loading : GetSlotsScreenUiState()
    data class Success(val data: TimeSlotsResponse) : GetSlotsScreenUiState()
    data class Error(val message : String) : GetSlotsScreenUiState()
}

sealed class CreateSlotsScreenUiState {
    object Idle : CreateSlotsScreenUiState()
    object Loading : CreateSlotsScreenUiState()
    object Success : CreateSlotsScreenUiState()
    data class Error(val message: String) : CreateSlotsScreenUiState()
}

sealed class GetDoctorProfile {
    object Loading : GetDoctorProfile()
    data class Success(val data: DoctorProfile) : GetDoctorProfile()
    data class Error(val message: String) : GetDoctorProfile()
}