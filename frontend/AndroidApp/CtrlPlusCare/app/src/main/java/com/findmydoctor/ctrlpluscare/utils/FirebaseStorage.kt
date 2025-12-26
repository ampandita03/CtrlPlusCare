package com.findmydoctor.ctrlpluscare.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

suspend fun uploadImageAndGetUrl(imageUri: Uri): String {
    val storageRef = FirebaseStorage.getInstance()
        .reference
        .child("profile_images/${System.currentTimeMillis()}.jpg")

    // Upload image
    storageRef.putFile(imageUri).await()

    // Get download URL
    return storageRef.downloadUrl.await().toString()
}
