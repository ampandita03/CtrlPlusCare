package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.DoctorSignUpRequest
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileRequest
import com.findmydoctor.ctrlpluscare.data.dto.SignIn
import com.findmydoctor.ctrlpluscare.data.dto.SignInOtp
import com.findmydoctor.ctrlpluscare.data.dto.SignInResult

interface AuthInterface {
    suspend fun signIn(signIn: SignIn): Result<Unit>
    suspend fun  signInOtp(signInOtp: SignInOtp): Result<SignInResult>

    suspend fun patientSignUp(patientSignUp: PatientProfileRequest): Result<Unit>

    suspend fun doctorSignUp(doctorSignUpRequest: DoctorSignUpRequest): Result<Unit>
}

