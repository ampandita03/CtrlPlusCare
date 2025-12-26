package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookAppointmentRequest(
    val doctorId: String,
    val date: String,
    val startTime: String,
    val endTime: String
)