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
    val userId: String,
    val name: String,
    val specialty: String,
    val clinicAddress: String,
    val consultationFee: Int,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

@Serializable
data class ClinicLocation(
    val type: String,
    val coordinates: List<Double>
)
