package com.findmydoctor.ctrlpluscare.ui.screens.splashscreen

sealed class SplashScreenUiState {
    object Loading : SplashScreenUiState()
    object PatientHome : SplashScreenUiState()
    object DoctorHome : SplashScreenUiState()
    object Welcome : SplashScreenUiState()

}