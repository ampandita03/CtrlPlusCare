package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup

import android.net.Uri
import android.util.Log
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
) : ViewModel() {

    companion object {
        private const val TAG = "PatientSignUpVM"
    }

    private val _uiState =
        MutableStateFlow<PatientSignUpUiState>(PatientSignUpUiState.Idle)
    val uiState: StateFlow<PatientSignUpUiState> = _uiState

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _fcmToken = MutableStateFlow<String?>(null)
    val fcmToken: StateFlow<String?> = _fcmToken

    private val _location =
        MutableStateFlow(Location(latitude = 0.0, longitude = 0.0))
    val location: StateFlow<Location> = _location

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    init {
        Log.d(TAG, "ViewModel initialized")
    }

    fun uploadProfileImage(imageUri: Uri) {
        Log.d(TAG, "uploadProfileImage called with uri=$imageUri")

        viewModelScope.launch {
            try {
                _isUploading.value = true
                Log.d(TAG, "Image upload started")

                val url = uploadImageAndGetUrl(imageUri)
                _imageUrl.value = url

                Log.d(TAG, "Image uploaded successfully, url=$url")
            } catch (e: Exception) {
                Log.e(TAG, "Image upload failed", e)
            } finally {
                _isUploading.value = false
                Log.d(TAG, "Image upload finished")
            }
        }
    }

    fun getFcmToken() {
        Log.d(TAG, "getFcmToken called")

        viewModelScope.launch {
            val token = localStorage.getFcmToken()
            _fcmToken.value = token

            Log.d(TAG, "FCM token fetched: $token")
        }
    }

    fun saveUserLocation(latitude: Double, longitude: Double) {
        Log.d(
            TAG,
            "saveUserLocation called with lat=$latitude, lng=$longitude"
        )

        viewModelScope.launch {
            val location = Location(latitude, longitude)
            _location.value = location
            localStorage.saveCurrentLocation(location)

            Log.d(TAG, "Location saved successfully: $location")
        }
    }

    fun signUp(patientProfileRequest: PatientProfileRequest) {
        Log.d(TAG, "signUp called with request=$patientProfileRequest")

        viewModelScope.launch {
            _uiState.value = PatientSignUpUiState.Loading
            Log.d(TAG, "SignUp started")

            val result = patientSignUpUseCase(patientProfileRequest)

            result.onSuccess {
                Log.d(TAG, "SignUp success")
                _uiState.value = PatientSignUpUiState.Success
            }

            result.onFailure { error ->
                Log.e(TAG, "SignUp failed: ${error.message}", error)
                _uiState.value =
                    PatientSignUpUiState.Error(error.message ?: "Unknown error")
            }
        }
    }
}
