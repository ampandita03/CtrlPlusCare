package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.BookAppointmentRequest
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingRequest
import com.findmydoctor.ctrlpluscare.domain.usecases.EmergencyBookingUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.BookingScreenUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreenUiStates
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmergencyDoctorInfoViewModel(
    private val localStorage: LocalStorage,
    private val emergencyBookingUseCase: EmergencyBookingUseCase
): ViewModel() {
    private val _doctor = MutableStateFlow<CurrentDoctorUiState>(CurrentDoctorUiState.Loading)
    val doctor: StateFlow<CurrentDoctorUiState> = _doctor

    private val _uiState = MutableStateFlow<EmergencyDoctorInfoUiState>(EmergencyDoctorInfoUiState.Idle)
    val uiState: StateFlow<EmergencyDoctorInfoUiState> = _uiState


    fun getCurrentDoctor(){
        viewModelScope.launch {
            val current = localStorage.getCurrentDoctor()

            _doctor.value = CurrentDoctorUiState.Success(current!!)
        }
    }

    fun bookEmergencyAppointment(emergencyBookingRequest: EmergencyBookingRequest){
        viewModelScope.launch {
            _uiState.value = EmergencyDoctorInfoUiState.Loading

            val result = emergencyBookingUseCase(emergencyBookingRequest)
            result.onSuccess {
                localStorage.saveCurrentEmergencyBooking(it)

                _uiState.value = EmergencyDoctorInfoUiState.Success(it)
            }
            result.onFailure {
                _uiState.value = EmergencyDoctorInfoUiState.Error(it.message ?: "Unknown Error"
                )
            }
        }
    }
}