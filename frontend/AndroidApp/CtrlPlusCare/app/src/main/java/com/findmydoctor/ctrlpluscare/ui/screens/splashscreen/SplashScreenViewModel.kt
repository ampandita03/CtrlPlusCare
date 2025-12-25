package com.findmydoctor.ctrlpluscare.ui.screens.splashscreen

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
    suspend fun navigate(){
        val token = localStorage.getToken()
        if (token!=null)
            _uiState.value = SplashScreenUiState.Home
        else
        _uiState.value = SplashScreenUiState.Welcome
    }


}