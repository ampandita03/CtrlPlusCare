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
            Log.d("EmergencyConfirmVM", "➡️ Fetching current doctor from local storage")

            val current = localStorage.getCurrentDoctor()

            if (current == null) {
                Log.e("EmergencyConfirmVM", "❌ No doctor found in local storage")
                return@launch
            }

            Log.d(
                "EmergencyConfirmVM",
                "✅ Doctor loaded | id=${current._id}, name=${current.name}"
            )

            _doctor.value = CurrentDoctorUiState.Success(current)
        }
    }

    fun getBookingData() {
        viewModelScope.launch {
            Log.d("EmergencyConfirmVM", "➡️ Fetching emergency booking data")

            val data = localStorage.getCurrentEmergencyBooking()

            if (data == null) {
                Log.e("EmergencyConfirmVM", "❌ No emergency booking found")
                return@launch
            }

            Log.d(
                "EmergencyConfirmVM",
                "✅ Booking loaded | date=$data"
            )

            _booking.value = EmergencyDoctorInfoUiState.Success(data)
        }
    }
}
