package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PatientProfileRequest(
    val name: String,
    val age: Int,
    val gender: String = "MALE",
    val address: String,
    val phoneNumber: String,
    val height: String,
    val weight: String,
    val location: ClinicLocation,
    val imageLink: String,
    val role: String,
    val fcmToken: String
)


/*
@Serializable
data class GetPatientProfile(

)*/
