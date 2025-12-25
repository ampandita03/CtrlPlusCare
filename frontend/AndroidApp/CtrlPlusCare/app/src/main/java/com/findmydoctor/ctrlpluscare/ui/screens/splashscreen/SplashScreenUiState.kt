package com.findmydoctor.ctrlpluscare.ui.screens.splashscreen

sealed class SplashScreenUiState {
    object Loading : SplashScreenUiState()
    object Home : SplashScreenUiState()
    object Welcome : SplashScreenUiState()

}