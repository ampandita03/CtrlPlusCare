package com.findmydoctor.ctrlpluscare.ui.screens.splashscreen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.findmydoctor.ctrlpluscare.utils.GenerateFCMToken
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashScreenViewModel(
    private val generateFCMToken: GenerateFCMToken,
    private val localStorage: LocalStorage
): ViewModel() {

    private val _uiState = MutableStateFlow<SplashScreenUiState>(SplashScreenUiState.Loading)
    val uiState : StateFlow<SplashScreenUiState> = _uiState

    suspend fun generateFcmToken(){
        generateFCMToken.generateAndStore()
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun navigate(){
        val token = localStorage.getToken()
        val role = localStorage.getUserRole()
        if (token!=null&&role =="DOCTOR")
            _uiState.value = SplashScreenUiState.DoctorHome
        else if(token!=null&&role =="PATIENT")
            _uiState.value = SplashScreenUiState.PatientHome
        else
        _uiState.value = SplashScreenUiState.Welcome
    }


}