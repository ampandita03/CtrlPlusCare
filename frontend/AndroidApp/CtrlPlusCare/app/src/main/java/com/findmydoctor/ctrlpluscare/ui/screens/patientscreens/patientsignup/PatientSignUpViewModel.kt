package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Location
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileRequest
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientSignUpUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import com.findmydoctor.ctrlpluscare.utils.uploadImageAndGetUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientSignUpViewModel(
    private val patientSignUpUseCase: PatientSignUpUseCase,
    private val localStorage: LocalStorage
) : ViewModel(){
    private val _uiState = MutableStateFlow<PatientSignUpUiState>(PatientSignUpUiState.Idle)
    val uiState: StateFlow<PatientSignUpUiState> = _uiState

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _fcmToken = MutableStateFlow<String?>(null)
    val fcmToken: StateFlow<String?> = _fcmToken

    private val _location = MutableStateFlow(Location(latitude = 0.0, longitude = 0.0))
    val location: StateFlow<Location> = _location

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                _isUploading.value = true
                val url = uploadImageAndGetUrl(imageUri)
                _imageUrl.value = url
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun getFcmToken(){
        viewModelScope.launch {
            _fcmToken.value = localStorage.getFcmToken()
        }
    }


    fun saveUserLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _location.value = Location(latitude, longitude)
            localStorage.saveCurrentLocation(Location(latitude,longitude))
        }
    }

    fun signUp(patientProfileRequest: PatientProfileRequest){
        viewModelScope.launch {
            _uiState.value = PatientSignUpUiState.Loading
            val result= patientSignUpUseCase(patientProfileRequest)

            result.onSuccess {
                _uiState.value = PatientSignUpUiState.Success
            }

            result.onFailure {
                _uiState.value = PatientSignUpUiState.Error(it.message ?: "Unknown error")

            }

        }
    }



}