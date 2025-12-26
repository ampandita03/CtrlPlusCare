package com.findmydoctor.ctrlpluscare.data.repository

import android.util.Log
import com.findmydoctor.ctrlpluscare.constant.Constants
import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlot
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

class SlotsImplementation(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorage
) : SlotsInterface{
    override suspend fun getAvailableTimeSlots(
        doctorId: String,
        date: String
    ): Result<TimeSlotsResponse> {
        return runCatching {
            val token = localStorage.getToken()
            val response = httpClient.get("${Constants.SERVER_ADDRESS}api/availability/slots") {
                url {
                    parameters.append("doctorId", doctorId)
                    parameters.append("date", date)
                }
                headers{
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            Log.d("TimeSlot","doctor id = $doctorId")
            if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                throw Exception("OTP verification failed: ${response.status} - $errorBody")
            }


            response.body<TimeSlotsResponse>()
        }
    }

}