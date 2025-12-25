package com.findmydoctor.ctrlpluscare.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.data.dto.SignIn
import com.findmydoctor.ctrlpluscare.data.dto.SignInOtp
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInOtpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val signInOtpUseCase: SignInOtpUseCase,
    private val localStorage: LocalStorage
) : ViewModel(){
    private val _uiState = MutableStateFlow<LoginUiStates>(LoginUiStates.Idle)
    val uiState : StateFlow<LoginUiStates> = _uiState


    fun signIn(phone: String){
        _uiState.value = LoginUiStates.Loading
        viewModelScope.launch {
            val result = signInUseCase(SignIn(phone))

            result.onSuccess {
                _uiState.value = LoginUiStates.OtpSent
            }
                .onFailure {
                    _uiState.value = LoginUiStates.Error("${it.message}")
                }
        }
    }

    fun resetUiState(){
        _uiState.value = LoginUiStates.Idle
    }
    fun signInOtp(phone: String, otp: String) {
        _uiState.value = LoginUiStates.Loading
        viewModelScope.launch {
            val fcmToken = localStorage.getFcmToken()
            Log.d("FCM", "FCM token sent to server: $fcmToken")
            val result = signInOtpUseCase(SignInOtp(phone, otp, fcmToken ?: "123"))

            result.onSuccess {
                _uiState.value = LoginUiStates.LoginSuccess(it)

            }
                .onFailure {
                    _uiState.value = LoginUiStates.Error("${it.message}")
                }

        }
    }
}