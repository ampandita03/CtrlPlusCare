package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorsignup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.DoctorSignUpRequest
import com.findmydoctor.ctrlpluscare.data.dto.Location
import com.findmydoctor.ctrlpluscare.domain.usecases.DoctorSignUpUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup.PatientSignUpUiState
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import com.findmydoctor.ctrlpluscare.utils.uploadImageAndGetUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoctorSignUpViewModel(
    private val localStorage: LocalStorage,
    private val doctorSignUpUseCase: DoctorSignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PatientSignUpUiState>(PatientSignUpUiState.Idle)
    val uiState: StateFlow<PatientSignUpUiState> = _uiState

    // Profile image
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _isUploadingProfile = MutableStateFlow(false)
    val isUploadingProfile: StateFlow<Boolean> = _isUploadingProfile

    // Document image
    private val _documentUrl = MutableStateFlow<String?>(null)
    val documentUrl: StateFlow<String?> = _documentUrl

    private val _isUploadingDocument = MutableStateFlow(false)
    val isUploadingDocument: StateFlow<Boolean> = _isUploadingDocument

    private val _fcmToken = MutableStateFlow<String?>(null)
    val fcmToken: StateFlow<String?> = _fcmToken

    private val _location = MutableStateFlow(Location(latitude = 0.0, longitude = 0.0))
    val location: StateFlow<Location> = _location

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                _isUploadingProfile.value = true
                val url = uploadImageAndGetUrl(imageUri)
                _imageUrl.value = url
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isUploadingProfile.value = false
            }
        }
    }

    fun uploadDocumentImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                _isUploadingDocument.value = true
                val url = uploadImageAndGetUrl(imageUri)
                _documentUrl.value = url
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isUploadingDocument.value = false
            }
        }
    }

    fun getFcmToken() {
        viewModelScope.launch {
            _fcmToken.value = localStorage.getFcmToken()
        }
    }

    fun saveUserLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _location.value = Location(latitude, longitude)
            localStorage.saveCurrentLocation(Location(latitude, longitude))
        }
    }

    fun signUp(doctorSignUpRequest: DoctorSignUpRequest) {
        viewModelScope.launch {
            _uiState.value = PatientSignUpUiState.Loading
            val result = doctorSignUpUseCase(doctorSignUpRequest)

            result.onSuccess {
                _uiState.value = PatientSignUpUiState.Success
            }

            result.onFailure {
                _uiState.value = PatientSignUpUiState.Error(it.message ?: "Unknown error")
            }
        }
    }
}