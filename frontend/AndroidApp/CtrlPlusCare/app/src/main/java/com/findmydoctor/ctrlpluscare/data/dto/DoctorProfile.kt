package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class DoctorProfileResponse(
    val success: Boolean,
    val data: DoctorProfile
)

@Serializable
data class DoctorProfile(
    val clinicLocation: ClinicLocation,

    val _id: String,
    val name: String,
    val specialty: String,
    val clinicAddress: String,

    val consultationFee: Int,
    val emergencyFee: Int,

    val email: String,
    val phoneNumber: String,
    val about: String,

    val profileUrl: String,
    val documentUrl: String,

    val rating: Int,
    val isVerified: Boolean,

    val fcmToken: String,
    val role: String,

    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

@Serializable
data class PatientAppointmentResponse(
    val success: Boolean,
    val data: List<PatientAppointment>
)

@Serializable
data class PatientAppointment(
    val _id: String,
    val doctorId: String,
    val patientId: String,
    val date: String,          // YYYY-MM-DD
    val startTime: String,     // HH:mm
    val endTime: String,       // HH:mm
    val status: String,        // BOOKED / CANCELLED
    val paymentStatus: String, // PENDING / PAID
    val isEmergency: Boolean,
    val fees: Int,
    val reminderSent: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)



@Serializable
data class UpdateDoctorProfileRequest(
    val name: String? = null,
    val clinicAddress: String? = null,
    val consultationFee: Int? = null,
    val emergencyFee: Int? = null,
    val about: String? = null,
    val profileUrl: String? = null,
    val clinicLocation: UpdateClinicLocation? = null
)



@Serializable
data class UpdateClinicLocation(
    val type: String = "Point",
    val coordinates: List<Double>? = null
)
