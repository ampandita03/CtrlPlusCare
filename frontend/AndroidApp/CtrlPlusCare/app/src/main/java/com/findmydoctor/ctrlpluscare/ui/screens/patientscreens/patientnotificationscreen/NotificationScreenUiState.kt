package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientnotificationscreen

import com.findmydoctor.ctrlpluscare.data.dto.NotificationResponse

sealed class NotificationScreenUiState {
    object Loading : NotificationScreenUiState()
    data class Success(val notifications: NotificationResponse) : NotificationScreenUiState()
    data class Error(val message: String): NotificationScreenUiState()
}

sealed class MarkNotificationUiState{
    object Loading : MarkNotificationUiState()
    object Success : MarkNotificationUiState()
    data class Error(val message: String): MarkNotificationUiState()
}


sealed class MarkAllNotificationUiState{
    object Loading : MarkAllNotificationUiState()
    object Success : MarkAllNotificationUiState()
    data class Error(val message: String): MarkAllNotificationUiState()
}