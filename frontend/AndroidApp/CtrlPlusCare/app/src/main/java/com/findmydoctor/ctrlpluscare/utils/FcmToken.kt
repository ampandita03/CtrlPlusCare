package com.findmydoctor.ctrlpluscare.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class GenerateFCMToken(
    private val localStorage: LocalStorage
) {

    suspend fun generateAndStore() {
        try {
            val token = FirebaseMessaging.getInstance().token.await()

            if (!token.isNullOrBlank()) {
                localStorage.saveFcmToken(token)
                Log.d("FCM", "FCM token saved: $token")
            }
        } catch (e: Exception) {
            Log.e("FCM", "Failed to fetch FCM token", e)
        }
    }
}