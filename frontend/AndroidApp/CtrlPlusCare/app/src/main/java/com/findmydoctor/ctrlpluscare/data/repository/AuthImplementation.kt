package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.DoctorSignUpRequest
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileRequest
import com.findmydoctor.ctrlpluscare.data.dto.SignIn
import com.findmydoctor.ctrlpluscare.data.dto.SignInOtp
import com.findmydoctor.ctrlpluscare.data.dto.SignInResult
import com.findmydoctor.ctrlpluscare.domain.interfaces.AuthInterface
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

class AuthImplementation(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorage
) : AuthInterface{
    override suspend fun signIn(signIn: SignIn): Result<Unit> {
        return runCatching {
            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/auth/send-otp") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                }
                setBody(signIn)
            }

            if (!response.status.isSuccess()) {
                throw Exception("Failed with status: ${response.status}")
            }

            Unit
        }
    }


    override suspend fun signInOtp(
        signInOtp: SignInOtp
    ): Result<SignInResult> {

        return runCatching {

            Log.d("SignIn", "➡️ Verify OTP request started")
            Log.d("SignIn", "Request body: $signInOtp")

            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/auth/verify-otp") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                }
                setBody(signInOtp)
            }

            Log.d("SignIn", "⬅️ Response received")
            Log.d("SignIn", "Status: ${response.status}")

            val rawBody = response.bodyAsText()
            Log.d("SignIn", "Raw response body: $rawBody")

            if (!response.status.isSuccess()) {
                Log.e(
                    "SignIn",
                    "❌ OTP verification failed | Status=${response.status} | Body=$rawBody"
                )
                throw Exception("OTP verification failed: ${response.status}")
            }

            val parsedResponse =
                Json.decodeFromString<SignInResult>(rawBody)

            Log.d("SignIn", "✅ Parsed response: $parsedResponse")
            Log.d("SignIn", "Saving token...")

            localStorage.saveToken(parsedResponse.data.token)

            Log.d("SignIn", "✅ Token saved successfully")

            parsedResponse
        }
    }


    override suspend fun patientSignUp(
        patientSignUp: PatientProfileRequest
    ): Result<Unit> {

        return runCatching {

            Log.d("PatientSignUp", "➡️ Signup request started")
            Log.d("PatientSignUp", "Request body: $patientSignUp")

            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/patient/signup") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                }
                setBody(patientSignUp)
            }

            Log.d("PatientSignUp", "⬅️ Response received")
            Log.d("PatientSignUp", "Status code: ${response.status}")

            val rawBody = response.bodyAsText()
            Log.d("PatientSignUp", "Raw response body: $rawBody")

            if (!response.status.isSuccess()) {
                Log.e(
                    "PatientSignUp",
                    "❌ Signup failed | Status=${response.status} | Body=$rawBody"
                )
                throw Exception("Signup failed: ${response.status}")
            }

            Log.d("PatientSignUp", "✅ Signup successful")

            Unit
        }
    }

    override suspend fun doctorSignUp(
        doctorSignUpRequest: DoctorSignUpRequest
    ): Result<Unit> {

        return runCatching {

            Log.d("DoctorSignUp", "➡️ Doctor signup request started")
            Log.d("DoctorSignUp", "Request body: $doctorSignUpRequest")

            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/doctors") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                }
                setBody(doctorSignUpRequest)
            }

            Log.d("DoctorSignUp", "⬅️ Response received")
            Log.d("DoctorSignUp", "Status: ${response.status}")

            val rawBody = response.bodyAsText()
            Log.d("DoctorSignUp", "Raw response body: $rawBody")

            if (!response.status.isSuccess()) {
                Log.e(
                    "DoctorSignUp",
                    "❌ Doctor signup failed | Status=${response.status} | Body=$rawBody"
                )
                throw Exception("Doctor signup failed: ${response.status}")
            }

            Log.d("DoctorSignUp", "✅ Doctor signup successful")

            Unit
        }
    }


}