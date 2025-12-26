package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PatientProfileResponse(
    val success: Boolean,
    val data: PatientProfile
)
@Serializable
data class PatientProfile(
    val _id: String,

    val name: String,
    val age: Int,
    val gender: String,

    val address: String,
    val height: String,
    val weight: String,

    val phoneNumber: String,
    val imageLink: String,

    val fcmToken: String,
    val role: String,          // "PATIENT"

    val location: Location,

    val createdAt: String,
    val updatedAt: String,

    val __v: Int
)
