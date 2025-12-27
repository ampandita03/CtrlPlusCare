package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface
import com.findmydoctor.ctrlpluscare.domain.usecases.CancelAppointmentsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAppointmentsUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientScheduleScreenViewModel(
    private val localStorage: LocalStorage,
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val cancelAppointmentsUseCase: CancelAppointmentsUseCase
): ViewModel() {


    private val _uiState = MutableStateFlow<PatientScheduleScreenUiState>(
        PatientScheduleScreenUiState.Idle)
    val uiState : StateFlow<PatientScheduleScreenUiState> = _uiState

    private val _cancelAppointmentUiState = MutableStateFlow<CancelAppointmentUiState>(
        CancelAppointmentUiState.Loading)
    val cancelAppointmentUiState : StateFlow<CancelAppointmentUiState> = _cancelAppointmentUiState




    fun getAppointments(){
        viewModelScope.launch {
            _uiState.value = PatientScheduleScreenUiState.Loading
            val result = getAppointmentsUseCase()

            result.onSuccess {
                _uiState.value = PatientScheduleScreenUiState.Success(it)
            }
            result.onFailure {
                _uiState.value = PatientScheduleScreenUiState.Error(it.message.toString())
            }
        }
    }

    fun cancelAppointments(appointmentId : String){
        viewModelScope.launch {
            _cancelAppointmentUiState.value = CancelAppointmentUiState.Loading
            val result = cancelAppointmentsUseCase(appointmentId)
            result.onSuccess {
                _cancelAppointmentUiState.value = CancelAppointmentUiState.Success
            }

            result.onFailure {
                _cancelAppointmentUiState.value =
                    CancelAppointmentUiState.Error(it.message.toString())

            }
        }
    }
}