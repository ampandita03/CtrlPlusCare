package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class SetSlotsRequest(

    val date: String,
    val startTime: String,
    val endTime: String,
    val slotDuration: Int
)
@Serializable
data class TimeSlotsResponse(
    val success: Boolean,
    val data: List<TimeSlot>?
)

@Serializable
data class TimeSlot(
    val startTime: String,
    val isAvailable : Boolean,
    val endTime: String
)

@Serializable
data class AppointmentsResponse(
    val success: Boolean,
    val data: List<Appointment>
)

@Serializable
data class Appointment(
    val _id: String,

    val doctorId: Doctor,        // 🔥 FULL doctor object
    val patientId: String,

    val date: String,
    val startTime: String,
    val endTime: String,

    val status: String,          // "BOOKED"
    val paymentStatus: String,   // "PENDING"

    val isEmergency: Boolean,
    val fees: Int,

    val reminderSent: Boolean,

    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)
