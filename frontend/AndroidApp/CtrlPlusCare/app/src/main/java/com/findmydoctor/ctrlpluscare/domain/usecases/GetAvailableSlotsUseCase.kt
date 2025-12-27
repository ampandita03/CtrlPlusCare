package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.SetSlotsRequest
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlotsResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface

class GetAvailableSlotsUseCase(
    private val slotsInterface: SlotsInterface
) {
    suspend operator fun invoke(doctorId: String,date:String): Result<TimeSlotsResponse>{
        return slotsInterface.getAvailableTimeSlots(doctorId,date)
    }
}

class SetSlotsUseCase(
    private val slotsInterface: SlotsInterface
){
    suspend operator fun invoke(setSlotsRequest: SetSlotsRequest): Result<Unit>{
        return slotsInterface.setSlots(setSlotsRequest)
    }
}