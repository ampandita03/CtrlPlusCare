package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse
import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdatePatientProfile

interface PatientInterface {
    suspend fun doctorsNearby(longitude : Double,latitude: Double): Result<DoctorResponse>

    suspend fun getAllDoctors() : Result<DoctorResponse>

    suspend fun getMyAppointments() : Result<AppointmentsResponse>
    suspend fun cancelMyAppointment(appointmentId : String) : Result<Unit>


    suspend fun getPatientProfile() : Result<PatientProfileResponse>
    suspend fun updatePatientProfile(updatePatientProfile: UpdatePatientProfile) : Result<Unit>
}