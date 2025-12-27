package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude : Double,
    val longitude : Double
)

@Serializable
data class SignIn(
    val phone : String
)
@Serializable
data class SignInOtp(
    val phone : String,
    val otp : String,
    val fcmToken : String
)

@Serializable
data class SignInResult(
    val success: Boolean,
    val data: SignInData
)

@Serializable
data class SignInData(
    val user: UserSignIn,
    val token: String
)

@Serializable
data class UserSignIn(
    val id : String,
    val role : String,
    val phone: String
)
@Serializable
data class User(
    val _id: String,
    val phone: String,
    val role: String,
    val otp: String?,        // nullable
    val otpExpiry: String?,  // nullable
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val fcmToken: String
)

