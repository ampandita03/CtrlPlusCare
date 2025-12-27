package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientAppointmentResponse

interface DoctorsInterface {
    suspend fun getDoctorProfile(): Result<DoctorProfileResponse>

    suspend fun getPatients(): Result<PatientAppointmentResponse>

}