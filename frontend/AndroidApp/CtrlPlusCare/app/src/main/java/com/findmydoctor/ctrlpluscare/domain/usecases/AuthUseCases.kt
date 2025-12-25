package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.SignIn
import com.findmydoctor.ctrlpluscare.data.dto.SignInOtp
import com.findmydoctor.ctrlpluscare.data.dto.SignInResult
import com.findmydoctor.ctrlpluscare.domain.interfaces.AuthInterface

class SignInUseCase(private val authInterface: AuthInterface){
    suspend operator fun invoke(signIn: SignIn): Result<Unit> {
        return authInterface.signIn(signIn)
    }
}

class SignInOtpUseCase(private val authInterface: AuthInterface){
    suspend operator fun invoke(signInOtp: SignInOtp): Result<SignInResult> {
        return authInterface.signInOtp(signInOtp)
    }
}

