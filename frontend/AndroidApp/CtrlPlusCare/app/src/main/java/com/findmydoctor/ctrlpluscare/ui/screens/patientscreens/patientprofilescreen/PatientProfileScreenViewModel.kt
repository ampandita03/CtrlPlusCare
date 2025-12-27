package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdatePatientProfile
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientProfileUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.UpdatePatientProfileUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import com.findmydoctor.ctrlpluscare.utils.uploadImageAndGetUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class PatientProfileScreenViewModel(
    private val localStorage: LocalStorage,
    private val patientProfileUseCase: PatientProfileUseCase,
    private val updatePatientProfileUseCase: UpdatePatientProfileUseCase
) : ViewModel() {

    private val _profile =
        MutableStateFlow<PatientProfileUiState>(PatientProfileUiState.Idle)
    val profile: StateFlow<PatientProfileUiState> = _profile

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _updateProfile =
        MutableStateFlow<UpdatePatientProfileUiState>(UpdatePatientProfileUiState.Idle)
    val updateProfile: StateFlow<UpdatePatientProfileUiState> = _updateProfile

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    // 🔐 LOGOUT
    fun logOut() {
        viewModelScope.launch {
            Log.d("PatientProfileVM", "➡️ Logout started")
            localStorage.clearAll()
            Log.d("PatientProfileVM", "✅ Local storage cleared")
        }
    }

    // 🖼️ IMAGE UPLOAD
    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                Log.d("PatientProfileVM", "➡️ Image upload started")
                _isUploading.value = true

                val url = uploadImageAndGetUrl(imageUri)
                _imageUrl.value = url

                Log.d("PatientProfileVM", "✅ Image uploaded: $url")

            } catch (e: Exception) {
                Log.e("PatientProfileVM", "❌ Image upload failed", e)
            } finally {
                _isUploading.value = false
                Log.d("PatientProfileVM", "ℹ️ Image upload finished")
            }
        }
    }

    // 👤 FETCH PROFILE
    fun getPatientProfile() {
        viewModelScope.launch {
            Log.d("PatientProfileVM", "➡️ Fetching patient profile")
            _profile.value = PatientProfileUiState.Loading

            val result = patientProfileUseCase()

            result.onSuccess {
                Log.d("PatientProfileVM", "✅ Profile fetched successfully")
                _profile.value = PatientProfileUiState.PatientProfileLoaded(it)

                localStorage.savePatientProfile(it)
                Log.d("PatientProfileVM", "💾 Profile saved to local storage")
            }

            result.onFailure {
                Log.e(
                    "PatientProfileVM",
                    "❌ Failed to fetch profile: ${it.message}",
                    it
                )
                _profile.value =
                    PatientProfileUiState.Error(it.message ?: "Unknown error")
            }
        }
    }

    // ✏️ UPDATE PROFILE (PARTIAL)
    fun updatePatientProfile(update: UpdatePatientProfile) {
        viewModelScope.launch {
            Log.d("PatientProfileVM", "➡️ Update profile started")
            Log.d("PatientProfileVM", "Update payload: $update")

            _updateProfile.value = UpdatePatientProfileUiState.Loading

            val result = updatePatientProfileUseCase(update)

            result.onSuccess {
                Log.d("PatientProfileVM", "✅ Profile update API success")
                _updateProfile.value = UpdatePatientProfileUiState.Success

                val current =
                    (_profile.value as? PatientProfileUiState.PatientProfileLoaded)
                        ?.patientProfileResponse
                        ?.data

                current?.let {
                    val merged = it.copy(
                        name = update.name ?: it.name,
                        age = update.age ?: it.age,
                        gender = update.gender ?: it.gender,
                        address = update.address ?: it.address,
                        imageLink = update.imageLink ?: it.imageLink
                    )

                    _profile.value =
                        PatientProfileUiState.PatientProfileLoaded(
                            PatientProfileResponse(success = true, data = merged)
                        )

                    localStorage.savePatientProfile(
                        PatientProfileResponse(success = true, data = merged)
                    )

                    Log.d("PatientProfileVM", "🔄 UI + local profile updated")
                }
            }

            result.onFailure {
                Log.e(
                    "PatientProfileVM",
                    "❌ Profile update failed: ${it.message}",
                    it
                )
                _updateProfile.value =
                    UpdatePatientProfileUiState.Error(it.message ?: "Update failed")
            }
        }
    }
}
