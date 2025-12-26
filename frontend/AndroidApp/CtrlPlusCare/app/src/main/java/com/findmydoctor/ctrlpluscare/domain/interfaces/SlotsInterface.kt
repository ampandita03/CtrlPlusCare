package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.TimeSlot
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse

interface SlotsInterface {
    suspend fun getAvailableTimeSlots(doctorId: String, date: String): Result<TimeSlotsResponse>
}
