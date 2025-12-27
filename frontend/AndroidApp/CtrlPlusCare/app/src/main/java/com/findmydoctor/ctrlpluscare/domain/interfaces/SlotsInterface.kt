package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.BookAppointmentRequest
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingRequest
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingResponse
import com.findmydoctor.ctrlpluscare.data.dto.SetSlotsRequest
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlot
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse

interface SlotsInterface {
    suspend fun getAvailableTimeSlots(doctorId: String, date: String): Result<TimeSlotsResponse>

    suspend fun bookSlot(bookAppointmentRequest: BookAppointmentRequest): Result<Unit>

    suspend fun emergencyBooking(emergencyBookingRequest: EmergencyBookingRequest): Result<EmergencyBookingResponse>

    suspend fun setSlots(setSlotsRequest: SetSlotsRequest): Result<Unit>
}
