package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse
import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdatePatientProfile
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

class PatientImplementation(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorage
) : PatientInterface{
    override suspend fun doctorsNearby(
        longitude: Double,
        latitude: Double
    ): Result<DoctorResponse> {
        return runCatching {
            val testLat = 28.6139
            val testLong = 77.209
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

    override suspend fun getAllDoctors(): Result<DoctorResponse> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/doctors/all/doc") {
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


            response.body<DoctorResponse>()
        }
    }

    override suspend fun getMyAppointments(): Result<AppointmentsResponse> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/appointments/my") {
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


            response.body<AppointmentsResponse>()
        }
    }

    override suspend fun cancelMyAppointment(
        appointmentId: String
    ): Result<Unit> {
        return runCatching {

            val token = localStorage.getToken()

            Log.d("CancelAppointment", "➡️ Cancel appointment started")
            Log.d("CancelAppointment", "AppointmentId: $appointmentId")
            Log.d("CancelAppointment", "Token present: ${!token.isNullOrBlank()}")

            val response = httpClient.patch(
                "${Constants.SERVER_ADDRESS}api/appointments/$appointmentId/cancel"
            ) {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            Log.d("CancelAppointment", "⬅️ Response received")
            Log.d("CancelAppointment", "Status: ${response.status}")

            val rawBody = response.bodyAsText()
            Log.d("CancelAppointment", "Raw response body: $rawBody")

            if (!response.status.isSuccess()) {
                Log.e(
                    "CancelAppointment",
                    "❌ Cancel failed | Status=${response.status} | Body=$rawBody"
                )
                throw Exception("Cancel appointment failed: ${response.status}")
            }

            Log.d("CancelAppointment", "✅ Appointment cancelled successfully")

            Unit
        }
    }



    override suspend fun getPatientProfile(): Result<PatientProfileResponse> {
        return runCatching {

            val token = localStorage.getToken()
            Log.d("PatientProfile", "➡️ Fetch patient profile started")
            Log.d("PatientProfile", "Token present: ${!token.isNullOrBlank()}")

            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/patient/profile") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            Log.d("PatientProfile", "⬅️ Response received")
            Log.d("PatientProfile", "Status: ${response.status}")

            // ⚠️ Read body ONLY ONCE
            val rawBody = response.bodyAsText()
            Log.d("PatientProfile", "Raw response body: $rawBody")

            if (!response.status.isSuccess()) {
                Log.e(
                    "PatientProfile",
                    "❌ Failed to fetch profile | Status=${response.status} | Body=$rawBody"
                )
                throw Exception("Failed to fetch patient profile: ${response.status}")
            }

            val parsedResponse =
                Json.decodeFromString<PatientProfileResponse>(rawBody)

            Log.d("PatientProfile", "✅ Parsed profile: $parsedResponse")

            parsedResponse
        }
    }

    override suspend fun updatePatientProfile(
        updatePatientProfile: UpdatePatientProfile
    ): Result<Unit> {

        return runCatching {

            val token = localStorage.getToken()

            Log.d("UpdateProfile", "➡️ Update patient profile started")
            Log.d("UpdateProfile", "Request body: $updatePatientProfile")
            Log.d("UpdateProfile", "Token present: ${!token.isNullOrBlank()}")

            val response = httpClient.put("${Constants.SERVER_ADDRESS}api/patient/profile") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(updatePatientProfile)
            }

            Log.d("UpdateProfile", "⬅️ Response received")
            Log.d("UpdateProfile", "Status: ${response.status}")

            val rawBody = response.bodyAsText()
            Log.d("UpdateProfile", "Raw response body: $rawBody")

            if (!response.status.isSuccess()) {
                Log.e(
                    "UpdateProfile",
                    "❌ Profile update failed | Status=${response.status} | Body=$rawBody"
                )
                throw Exception("Profile update failed: ${response.status}")
            }

            Log.d("UpdateProfile", "✅ Profile updated successfully")

            Unit
        }
    }


}