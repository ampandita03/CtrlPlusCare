package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse

interface PatientInterface {
    suspend fun doctorsNearby(longitude : Double,latitude: Double): Result<DoctorResponse>

    suspend fun getPatientProfile() : Result<PatientProfileResponse>
}