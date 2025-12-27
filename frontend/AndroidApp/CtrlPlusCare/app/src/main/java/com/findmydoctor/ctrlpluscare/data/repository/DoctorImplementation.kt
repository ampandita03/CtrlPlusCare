package com.findmydoctor.ctrlpluscare.data.repository

import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse
import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientAppointmentResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

class DoctorImplementation(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorage
) : DoctorsInterface{
    override suspend fun getDoctorProfile(): Result<DoctorProfileResponse> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/doctors/my") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }

            }

            /*Log.d("SignIn","$signInOtp")
            Log.d("SignIn","${response.body<SignInResult>()}")*/

            if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                throw Exception("OTP verification failed: ${response.status} - $errorBody")
            }


            response.body<DoctorProfileResponse>()
        }
    }

    override suspend fun getPatients(): Result<PatientAppointmentResponse> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/appointments/doc") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }

            }

            /*Log.d("SignIn","$signInOtp")
            Log.d("SignIn","${response.body<SignInResult>()}")*/

            if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                throw Exception("OTP verification failed: ${response.status} - $errorBody")
            }


            response.body<PatientAppointmentResponse>()
        }
    }
}