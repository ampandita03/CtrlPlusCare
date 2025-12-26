package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.data.dto.SignInResult
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

class DoctorImplementation(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorage
) : DoctorsInterface{
    override suspend fun doctorsNearby(
        longitude: Double,
        latitude: Double
    ): Result<DoctorResponse> {
        return runCatching {
            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/doctors/nearby") {
                url {
                    parameters.append("lat", latitude.toString())
                    parameters.append("lng", longitude.toString())
                }
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                }
            }

            /*Log.d("SignIn","$signInOtp")
            Log.d("SignIn","${response.body<SignInResult>()}")*/

            if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                throw Exception("OTP verification failed: ${response.status} - $errorBody")
            }


            response.body<DoctorResponse>()
        }
    }
}