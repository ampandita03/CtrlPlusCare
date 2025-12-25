package com.findmydoctor.ctrlpluscare.ui.screens.splashscreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashScreenViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow<SplashScreenUiState>(SplashScreenUiState.Loading)
    val uiState : StateFlow<SplashScreenUiState> = _uiState


    fun navigate(){

        _uiState.value = SplashScreenUiState.Welcome
    }


}