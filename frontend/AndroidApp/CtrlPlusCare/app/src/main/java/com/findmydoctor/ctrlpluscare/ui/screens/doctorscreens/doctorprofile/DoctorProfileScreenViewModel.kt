package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.Location
import com.findmydoctor.ctrlpluscare.data.dto.UpdateDoctorProfileRequest
import com.findmydoctor.ctrlpluscare.domain.usecases.GetDoctorProfileUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.UpdateDoctorProfileUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import com.findmydoctor.ctrlpluscare.utils.uploadImageAndGetUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class DoctorProfileScreenViewModel(
    private val localStorage: LocalStorage,
    private val getDoctorProfileUseCase: GetDoctorProfileUseCase,
    private val updateDoctorProfileUseCase: UpdateDoctorProfileUseCase
) : ViewModel() {

    private val TAG = "DoctorProfileVM"

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _profile =
        MutableStateFlow<DoctorProfileScreenUiStates>(DoctorProfileScreenUiStates.Idle)
    val profile: StateFlow<DoctorProfileScreenUiStates> = _profile

    private val _updateProfile =
        MutableStateFlow<UpdateDoctorProfileScreenUiStates>(UpdateDoctorProfileScreenUiStates.Idle)
    val updateProfile: StateFlow<UpdateDoctorProfileScreenUiStates> = _updateProfile

    private val _location = MutableStateFlow(Location(latitude = 0.0, longitude = 0.0))
    val location: StateFlow<Location> = _location


    fun getDoctorProfile() {
        viewModelScope.launch {
            Log.d(TAG, "➡️ Fetching doctor profile")
            _profile.value = DoctorProfileScreenUiStates.Loading

            getDoctorProfileUseCase()
                .onSuccess {
                    Log.d(TAG, "✅ Doctor profile fetched successfully")
                    _profile.value = DoctorProfileScreenUiStates.Success(it)
                }
                .onFailure {
                    Log.e(TAG, "❌ Failed to fetch doctor profile: ${it.message}", it)
                    _profile.value =
                        DoctorProfileScreenUiStates.Error(it.message ?: "Something went wrong")
                }
        }
    }

    fun updateDoctorProfile(updateDoctorProfileRequest: UpdateDoctorProfileRequest) {
        viewModelScope.launch {
            Log.d(TAG, "➡️ Updating doctor profile")
            Log.d(TAG, "📦 Update request: $updateDoctorProfileRequest")

            _updateProfile.value = UpdateDoctorProfileScreenUiStates.Loading

            updateDoctorProfileUseCase(updateDoctorProfileRequest)
                .onSuccess {
                    Log.d(TAG, "✅ Doctor profile updated successfully")
                    _updateProfile.value = UpdateDoctorProfileScreenUiStates.Success

                    // Refresh profile after update
                    Log.d(TAG, "🔄 Refreshing doctor profile after update")
                    getDoctorProfile()
                }
                .onFailure {
                    Log.e(TAG, "❌ Failed to update doctor profile: ${it.message}", it)
                    _updateProfile.value = UpdateDoctorProfileScreenUiStates.Error(
                        it.message ?: "Something went wrong"
                    )
                }
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "➡️ Image upload started")
                Log.d(TAG, "🖼️ Image URI: $imageUri")

                _isUploading.value = true

                val url = uploadImageAndGetUrl(imageUri)
                _imageUrl.value = url

                Log.d(TAG, "✅ Image uploaded successfully")
                Log.d(TAG, "🌐 Image URL: $url")

            } catch (e: Exception) {
                Log.e(TAG, "❌ Image upload failed", e)
            } finally {
                _isUploading.value = false
                Log.d(TAG, "ℹ️ Image upload finished")
            }
        }
    }

    fun saveUserLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            Log.d(TAG, "📍 Saving user location: lat=$latitude, lng=$longitude")

            val location = Location(latitude, longitude)
            _location.value = location
            localStorage.saveCurrentLocation(location)

            Log.d(TAG, "✅ User location saved to local storage")
        }
    }

    fun logOut() {
        viewModelScope.launch {
            Log.d(TAG, "🚪 Logging out user")
            localStorage.clearAll()
            Log.d(TAG, "🧹 Local storage cleared successfully")
        }
    }
}
