package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingconfirmed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.CurrentBookingUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingConfirmViewModel(
    private val localStorage: LocalStorage
) : ViewModel(){
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
    fun getBookingData(){
        viewModelScope.launch {
            val data = localStorage.getCurrentBooking()
            _booking.value = CurrentBookingUiState.Success(data!!)
        }
    }
}