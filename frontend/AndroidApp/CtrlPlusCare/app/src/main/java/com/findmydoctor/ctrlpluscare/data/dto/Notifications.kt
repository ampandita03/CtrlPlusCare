package com.findmydoctor.ctrlpluscare.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val success: Boolean,
    val data: List<NotificationItem>
)
@Serializable
data class NotificationItem(
    val _id: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean,
    val meta: NotificationMeta,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)
@Serializable
data class NotificationMeta(
    val appointmentId: String
)
@Serializable
enum class NotificationType {
    APPOINTMENT,
    EMERGENCY
}
