package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
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


    override suspend fun signInOtp(signInOtp: SignInOtp): Result<SignInResult> {
        return runCatching {
            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/auth/verify-otp") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                }
                setBody(signInOtp)
            }
            Log.d("SignIn","$signInOtp")
            Log.d("SignIn","${response.body<SignInResult>()}")

            if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                throw Exception("OTP verification failed: ${response.status} - $errorBody")
            }

            localStorage.saveToken(response.body<SignInResult>().data.token)
            response.body<SignInResult>()
        }
    }

}