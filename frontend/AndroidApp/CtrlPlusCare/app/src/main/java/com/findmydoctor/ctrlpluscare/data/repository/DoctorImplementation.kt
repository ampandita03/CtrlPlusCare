package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse
import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.NotificationResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientAppointmentResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdateDoctorProfileRequest
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
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
    override suspend fun updateDoctorProfile(
        updateDoctorProfileRequest: UpdateDoctorProfileRequest
    ): Result<Unit> {
        return runCatching {

            val token = localStorage.getToken()
            Log.d("Token", "$token")

            val body = mutableMapOf<String, Any>()

            updateDoctorProfileRequest.name?.let { body["name"] = it }
            updateDoctorProfileRequest.clinicAddress?.let { body["clinicAddress"] = it }
            updateDoctorProfileRequest.consultationFee?.let { body["consultationFee"] = it }
            updateDoctorProfileRequest.emergencyFee?.let { body["emergencyFee"] = it }
            updateDoctorProfileRequest.about?.let { body["about"] = it }
            updateDoctorProfileRequest.profileUrl?.let { body["profileUrl"] = it }
            updateDoctorProfileRequest.clinicLocation?.let { body["clinicLocation"] = it }

            Log.d("UpdateDoctorProfile", "📤 Clean body: $body")

            val response = httpClient.put( // 👈 PATCH like Postman
                "${Constants.SERVER_ADDRESS}api/doctors/profile"
            ) {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(body)
            }

            if (!response.status.isSuccess()) {
                val error = response.bodyAsText()
                throw Exception("Failed: ${response.status} - $error")
            }

            Unit
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

    override suspend fun getNotifications(): Result<NotificationResponse> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/notifications") {
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


            response.body<NotificationResponse>()
        }
    }

    override suspend fun markRead(notificationId: String): Result<Unit> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.patch("${Constants.SERVER_ADDRESS}api/$notificationId/read") {
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


            Unit
        }
    }

    override suspend fun markReadAll(): Result<Unit> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.patch ("${Constants.SERVER_ADDRESS}api/notifications/read-all") {
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


            Unit
        }
    }
}