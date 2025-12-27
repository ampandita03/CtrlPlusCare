package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookAppointmentRequest(
    val doctorId: String,
    val date: String,
    val startTime: String,
    val endTime: String
)

@Serializable
data class EmergencyBookingRequest(
    val doctorId: String,
    val date: String
)

@Serializable
data class EmergencyBookingResponse(
    val success: Boolean,
    val message: String,
    val data: EmergencyBooking
)

@Serializable
data class EmergencyBooking(
    val doctorId: String,
    val patientId: String,
    val date: String,          // yyyy-MM-dd
    val startTime: String,     // HH:mm
    val endTime: String,       // HH:mm
    val status: String,        // BOOKED
    val paymentStatus: String, // PENDING
    val isEmergency: Boolean,
    val fees: Int,
    val reminderSent: Boolean,
    val _id: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)
