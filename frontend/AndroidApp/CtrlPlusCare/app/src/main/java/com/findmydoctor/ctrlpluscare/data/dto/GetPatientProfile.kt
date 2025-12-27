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

    val location: Location1,

    val createdAt: String,
    val updatedAt: String,

    val __v: Int
)

@Serializable
data class Location1(
    val type: String,              // "Point"
    val coordinates: List<Double>  // [longitude, latitude]
)

@Serializable
data class UpdatePatientProfile(

    val name: String? = null,
    val age: Int? = null,
    val gender: String? = null,

    val address: String? = null,
    val height: String? = null,
    val weight: String? = null,

    val phoneNumber: String? = null,
    val imageLink: String? = null,
)