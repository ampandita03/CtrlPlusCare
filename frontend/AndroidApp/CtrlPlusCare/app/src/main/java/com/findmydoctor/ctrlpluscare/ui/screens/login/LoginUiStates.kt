package com.findmydoctor.ctrlpluscare.ui.screens.login

import com.findmydoctor.ctrlpluscare.data.dto.SignInResult

sealed class LoginUiStates {
    object Idle : LoginUiStates()
    object Loading : LoginUiStates()
    object OtpSent : LoginUiStates()
    data class LoginSuccess(val data : SignInResult) : LoginUiStates()
    data class Error(val message: String) : LoginUiStates()
}