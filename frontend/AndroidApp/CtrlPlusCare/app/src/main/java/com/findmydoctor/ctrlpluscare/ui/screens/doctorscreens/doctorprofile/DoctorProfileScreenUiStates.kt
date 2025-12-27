package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile

import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import org.koin.core.logger.MESSAGE

sealed class DoctorProfileScreenUiStates {
    object Idle : DoctorProfileScreenUiStates()
    object Loading : DoctorProfileScreenUiStates()
    data class Success(val data: DoctorProfileResponse) : DoctorProfileScreenUiStates()
    data class Error(val message: String) : DoctorProfileScreenUiStates()
}

sealed class UpdateDoctorProfileScreenUiStates {
    object Idle : UpdateDoctorProfileScreenUiStates()
    object Loading : UpdateDoctorProfileScreenUiStates()
    object Success : UpdateDoctorProfileScreenUiStates()
    data class Error(val message: String) : UpdateDoctorProfileScreenUiStates()
}
