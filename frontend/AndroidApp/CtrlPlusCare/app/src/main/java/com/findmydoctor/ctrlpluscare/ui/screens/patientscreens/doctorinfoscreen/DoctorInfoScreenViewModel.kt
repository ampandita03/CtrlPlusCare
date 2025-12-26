package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAvailableSlotsUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoctorInfoScreenViewModel(
    private val localStorage: LocalStorage,
    private val getAvailableSlotsUseCase: GetAvailableSlotsUseCase
) : ViewModel(){

    private val _doctor = MutableStateFlow<CurrentDoctorUiState>(CurrentDoctorUiState.Loading)
    val doctor: StateFlow<CurrentDoctorUiState> = _doctor

    private val _uiState = MutableStateFlow<DoctorInfoScreenUiStates>(DoctorInfoScreenUiStates.Idle)
    val uiState: StateFlow<DoctorInfoScreenUiStates> = _uiState


    fun getCurrentDoctor(){
        viewModelScope.launch {
            val current = localStorage.getCurrentDoctor()

            _doctor.value = CurrentDoctorUiState.Success(current!!)
        }
    }

    fun getAvailableSlots(date: String){
        viewModelScope.launch {
            val doctorId = localStorage.getCurrentDoctor()?.userId
            _uiState.value = DoctorInfoScreenUiStates.Loading

            val response = getAvailableSlotsUseCase(doctorId ?: "",date)
            response.onSuccess {
                _uiState.value = DoctorInfoScreenUiStates.Success(it)
            }
            response.onFailure {
                _uiState.value = DoctorInfoScreenUiStates.Error(it.message ?: "" )
            }
        }
    }
}