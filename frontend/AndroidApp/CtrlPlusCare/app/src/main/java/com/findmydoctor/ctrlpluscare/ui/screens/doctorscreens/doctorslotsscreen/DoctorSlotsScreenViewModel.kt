package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorslotsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.SetSlotsRequest
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAvailableSlotsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SetSlotsUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoctorSlotsScreenViewModel(
    private val localStorage: LocalStorage,
    private val setSlotsUseCase: SetSlotsUseCase,
    private val getAvailableSlotsUseCase: GetAvailableSlotsUseCase
): ViewModel() {

    private val _getSlots = MutableStateFlow<GetSlotsScreenUiState>(GetSlotsScreenUiState.Idle)
    val getSlots : StateFlow<GetSlotsScreenUiState> = _getSlots

    private val _createSlots = MutableStateFlow<CreateSlotsScreenUiState>(CreateSlotsScreenUiState.Idle)
    val createSlots: StateFlow<CreateSlotsScreenUiState> = _createSlots

    private val _profile = MutableStateFlow<GetDoctorProfile>(GetDoctorProfile.Loading)
    val profile: StateFlow<GetDoctorProfile> = _profile

    fun getAvailableSlots(date: String){
        _getSlots.value = GetSlotsScreenUiState.Loading
        viewModelScope.launch {
            val doctorId = localStorage.getDoctorProfile()?._id

            if(doctorId != null){
                val result = getAvailableSlotsUseCase(doctorId,date)
                result.onSuccess {
                    _getSlots.value = GetSlotsScreenUiState.Success(it)
                }
                    .onFailure {
                        _getSlots.value = GetSlotsScreenUiState.Error(it.message ?: "Unknown error")

                    }
            }
        }

    }
    fun getProfile(){
        _profile.value = GetDoctorProfile.Loading
        viewModelScope.launch {
            val result = localStorage.getDoctorProfile()
            if(result != null){
                _profile.value = GetDoctorProfile.Success(result)
            }
        }
    }
    fun createSlots(setSlotsRequest: SetSlotsRequest){
        _createSlots.value = CreateSlotsScreenUiState.Loading
        viewModelScope.launch {
            val result = setSlotsUseCase(setSlotsRequest)
            result.onSuccess {
                _createSlots.value = CreateSlotsScreenUiState.Success

            }
                .onFailure {
                    _createSlots.value =
                        CreateSlotsScreenUiState.Error(it.message ?: "Unknown error")

                }
        }
    }
}