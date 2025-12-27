package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse
import com.findmydoctor.ctrlpluscare.data.dto.BookAppointmentRequest
import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBooking
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingRequest
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingResponse
import com.findmydoctor.ctrlpluscare.data.dto.SetSlotsRequest
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlot
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface
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

class SlotsImplementation(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorage
) : SlotsInterface {

    override suspend fun getAvailableTimeSlots(
        doctorId: String,
        date: String
    ): Result<TimeSlotsResponse> {

        return runCatching {

            val token = localStorage.getToken()

            Log.d("TimeSlot", "➡️ Request started")
            Log.d("TimeSlot", "DoctorId: $doctorId")
            Log.d("TimeSlot", "Date: $date")
            Log.d("TimeSlot", "Token present: ${!token.isNullOrBlank()}")

            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/availability/slots") {
                url {
                    parameters.append("doctorId", doctorId) // MUST be valid ObjectId
                    parameters.append("date", date)
                }
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }


            Log.d("TimeSlot", "⬅️ Response received")
            Log.d("TimeSlot", "Status: ${response.status}")

            val rawBody = response.bodyAsText()

            if (!response.status.isSuccess()) {

                val errorMessage = parseApiError(
                    rawBody = rawBody,
                    defaultMessage = "Signup failed"
                )

                throw Exception(errorMessage)
            }


            val parsedResponse = response.body<TimeSlotsResponse>()
            Log.d("TimeSlot", "✅ Parsed response: $parsedResponse")

            parsedResponse
        }
    }

    override suspend fun bookSlot(
        bookAppointmentRequest: BookAppointmentRequest
    ): Result<Unit> {

        return runCatching {

            val token = localStorage.getToken()

            Log.d("BookSlot", "================ BOOK SLOT START ================")
            Log.d("BookSlot", "Request URL: ${Constants.SERVER_ADDRESS}api/appointments")
            Log.d("BookSlot", "Request body: $bookAppointmentRequest")
            Log.d("BookSlot", "Auth token present: ${!token.isNullOrBlank()}")

            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/appointments") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(bookAppointmentRequest)
            }

            val statusCode = response.status.value
            val statusText = response.status.description
            val rawBody = response.bodyAsText()

            Log.d("BookSlot", "⬅️ Response received")
            Log.d("BookSlot", "Status code: $statusCode ($statusText)")
            Log.d("BookSlot", "Raw response body: $rawBody")

            if (!response.status.isSuccess() && statusCode != 400) {

                val errorMessage = parseApiError(
                    rawBody = rawBody,
                    defaultMessage = "Failed to book slot"
                )

                Log.e(
                    "BookSlot",
                    "❌ Slot booking failed | Status=$statusCode | Message=$errorMessage"
                )

                throw Exception(errorMessage)
            }

            Log.d("BookSlot", "✅ Slot booked successfully")
            Log.d("BookSlot", "================ BOOK SLOT END ==================")

            Unit
        }.onFailure { e ->
            Log.e(
                "BookSlot",
                "🔥 Exception occurred while booking slot",
                e
            )
        }
    }


    override suspend fun emergencyBooking(emergencyBookingRequest: EmergencyBookingRequest): Result<EmergencyBookingResponse> {
        return runCatching {

            val token = localStorage.getToken()

            Log.d("BookSlot", "➡️ Book slot request started")
            Log.d("BookSlot", "Request body: $emergencyBookingRequest")
            Log.d("BookSlot", "Token present: ${!token.isNullOrBlank()}")

            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/appointments/book") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(emergencyBookingRequest)
            }

            Log.d("BookSlot", "⬅️ Response received")
            Log.d("BookSlot", "Status: ${response.status}")

            val rawBody = response.bodyAsText()
            Log.d("BookSlot", "Raw response body: $rawBody")


            if (!response.status.isSuccess()  && response.status.value != 400 ) {

                val errorMessage = parseApiError(
                    rawBody = rawBody,
                    defaultMessage = "Signup failed"
                )

                throw Exception(errorMessage)
            }


            Log.d("BookSlot", "✅ Slot booked successfully")

            response.body<EmergencyBookingResponse>()
        }
    }

    override suspend fun setSlots(setSlotsRequest: SetSlotsRequest): Result<Unit> {
        return runCatching {

            val token = localStorage.getToken()
            val response = httpClient.post("${Constants.SERVER_ADDRESS}api/availability") {
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                    setBody(setSlotsRequest)
            }

            /*Log.d("SignIn","$signInOtp")
            Log.d("SignIn","${response.body<SignInResult>()}")*/

            val rawBody = response.bodyAsText()

            if (!response.status.isSuccess()) {

                val errorMessage = parseApiError(
                    rawBody = rawBody,
                    defaultMessage = "Signup failed"
                )

                throw Exception(errorMessage)
            }



            Unit
        }
    }

}
