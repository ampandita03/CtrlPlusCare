package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class DoctorResponse(
    val success: Boolean,
    val data: List<Doctor>
)

@Serializable
data class Doctor(
    val clinicLocation: ClinicLocation,

    val _id: String,

    val userId: String? = null,          // ⚠️ optional (missing in 2nd object)

    val name: String,
    val specialty: String,
    val clinicAddress: String,
    val consultationFee: Int,

    val email: String,
    val phoneNumber: String,
    val emergencyFee:Int = 500,// "DOCTOR"

    val about: String,
    val profileUrl: String,
    val documentUrl: String,

    val rating: Int,
    val isVerified: Boolean,

    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

@Serializable
data class ClinicLocation(
    val type: String,
    val coordinates: List<Double>
)

@Serializable
data class DoctorSignUpRequest(
    val name: String,
    val specialty: String,
    val clinicLocation: ClinicLocation,
    val clinicAddress: String,
    val consultationFee: Int,
    val email: String,
    val phoneNumber: String,
    val about: String,
    val profileUrl: String,
    val documentUrl: String,
    val role: String,
    val emergencyFee:Int = 500,// "DOCTOR"
    val fcmToken: String
)

