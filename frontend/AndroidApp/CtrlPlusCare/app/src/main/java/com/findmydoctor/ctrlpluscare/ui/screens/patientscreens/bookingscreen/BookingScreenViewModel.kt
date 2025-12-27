package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.BookAppointmentRequest
import com.findmydoctor.ctrlpluscare.domain.usecases.BookingUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingScreenViewModel(
    private val localStorage: LocalStorage,
    private val bookingUseCase: BookingUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<BookingScreenUiState>(BookingScreenUiState.Idle)
    val uiState: StateFlow<BookingScreenUiState> = _uiState
    private val _doctor = MutableStateFlow<CurrentDoctorUiState>(CurrentDoctorUiState.Loading)
    val doctor: StateFlow<CurrentDoctorUiState> = _doctor

    private val _booking = MutableStateFlow<CurrentBookingUiState>(CurrentBookingUiState.Loading)
    val booking: StateFlow<CurrentBookingUiState> = _booking

    fun getCurrentDoctor(){
        viewModelScope.launch {
            val current = localStorage.getCurrentDoctor()

            _doctor.value = CurrentDoctorUiState.Success(current!!)
        }
    }

    fun bookAppointment(bookAppointmentRequest: BookAppointmentRequest){
        viewModelScope.launch {
            _uiState.value = BookingScreenUiState.Loading

            val result = bookingUseCase(bookAppointmentRequest)
            result.onSuccess {
                _uiState.value = BookingScreenUiState.Success
            }
            result.onFailure {
                _uiState.value = BookingScreenUiState.Error(it.message ?: "Unknown Error"
                )
            }
        }
    }
    fun getBookingData(){
        viewModelScope.launch {
            val data = localStorage.getCurrentBooking() ?: return@launch
            _booking.value = CurrentBookingUiState.Success(data)

        }
    }
}