package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.BookAppointmentRequest
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingRequest
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface

class BookingUseCase(
    private val slotsInterface: SlotsInterface
) {
    suspend operator fun invoke(bookAppointmentRequest: BookAppointmentRequest): Result<Unit>{
        return slotsInterface.bookSlot(bookAppointmentRequest)
    }
}

class EmergencyBookingUseCase(
    private val slotsInterface: SlotsInterface
){
    suspend operator fun invoke(emergencyBookingRequest: EmergencyBookingRequest): Result<EmergencyBookingResponse>{
        return slotsInterface.emergencyBooking(emergencyBookingRequest)
    }
}