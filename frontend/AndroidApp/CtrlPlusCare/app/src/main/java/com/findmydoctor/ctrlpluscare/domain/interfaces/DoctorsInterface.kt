package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse

interface DoctorsInterface {
    suspend fun doctorsNearby(longitude : Double,latitude: Double): Result<DoctorResponse>

}