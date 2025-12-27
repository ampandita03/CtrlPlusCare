package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencybookingconfirmscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.CurrentBookingUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen.EmergencyDoctorInfoUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmergencyBookingConfirmViewModel(
    private val localStorage: LocalStorage
) : ViewModel() {

    private val _doctor =
        MutableStateFlow<CurrentDoctorUiState>(CurrentDoctorUiState.Loading)
    val doctor: StateFlow<CurrentDoctorUiState> = _doctor

    private val _booking =
        MutableStateFlow<EmergencyDoctorInfoUiState>(EmergencyDoctorInfoUiState.Idle)
    val booking: StateFlow<EmergencyDoctorInfoUiState> = _booking

    fun getCurrentDoctor() {
        viewModelScope.launch {
            _doctor.value = CurrentDoctorUiState.Loading

            val current = localStorage.getCurrentDoctor()

            if (current == null) {
                _doctor.value = CurrentDoctorUiState.Error(
                    message = "Doctor information not available"
                )
                return@launch
            }

            _doctor.value = CurrentDoctorUiState.Success(current)
        }
    }


    fun getBookingData() {
        viewModelScope.launch {
            _booking.value = EmergencyDoctorInfoUiState.Loading

            Log.d("EmergencyConfirmVM", "➡️ Fetching emergency booking data")

            val data = localStorage.getCurrentEmergencyBooking()

            if (data == null) {
                Log.e("EmergencyConfirmVM", "❌ No emergency booking found")

                _booking.value = EmergencyDoctorInfoUiState.Error(
                    message = "No emergency booking found"
                )
                return@launch
            }

            Log.d("EmergencyConfirmVM", "✅ Booking loaded")

            _booking.value = EmergencyDoctorInfoUiState.Success(data)
        }
    }

}
