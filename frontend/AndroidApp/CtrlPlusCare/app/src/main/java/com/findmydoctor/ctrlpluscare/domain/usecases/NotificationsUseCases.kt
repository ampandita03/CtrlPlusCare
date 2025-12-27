package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.NotificationResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface

class GetNotificationUseCase(
    private val doctorsInterface: DoctorsInterface
){
    suspend operator fun invoke(): Result<NotificationResponse>{
        return doctorsInterface.getNotifications()
    }
}

class MarkNotificationUseCase(
    private val doctorsInterface: DoctorsInterface
){
    suspend operator fun invoke(notificationId: String): Result<Unit>{
        return doctorsInterface.markRead(notificationId)
    }
}

class MarkAllNotificationUseCase(
    private val doctorsInterface: DoctorsInterface
){
    suspend operator fun invoke(): Result<Unit>{
        return doctorsInterface.markReadAll()
    }
}