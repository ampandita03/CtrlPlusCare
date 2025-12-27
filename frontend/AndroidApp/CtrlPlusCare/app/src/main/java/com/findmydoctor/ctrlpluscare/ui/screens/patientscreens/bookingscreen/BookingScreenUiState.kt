package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen

import com.findmydoctor.ctrlpluscare.data.dto.BookAppointmentRequest

sealed class BookingScreenUiState {

    object Idle: BookingScreenUiState()
    object Loading : BookingScreenUiState()
    object Success : BookingScreenUiState()
    data class Error(val message: String) : BookingScreenUiState()

}

sealed class CurrentBookingUiState {

    object Loading: CurrentBookingUiState()
    data class Success(val data : BookAppointmentRequest) : CurrentBookingUiState()


}

