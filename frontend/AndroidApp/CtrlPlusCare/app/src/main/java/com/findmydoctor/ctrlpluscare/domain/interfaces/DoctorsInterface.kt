package com.findmydoctor.ctrlpluscare.domain.interfaces

import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.NotificationResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientAppointmentResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdateDoctorProfileRequest

interface DoctorsInterface {
    suspend fun getDoctorProfile(): Result<DoctorProfileResponse>

    suspend fun updateDoctorProfile(updateDoctorProfileRequest: UpdateDoctorProfileRequest): Result<Unit>

    suspend fun getPatients(): Result<PatientAppointmentResponse>

    suspend fun getNotifications() : Result<NotificationResponse>

    suspend fun markRead(notificationId: String): Result<Unit>
    suspend fun markReadAll(): Result<Unit>
}
