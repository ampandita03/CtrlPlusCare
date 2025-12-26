package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

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
