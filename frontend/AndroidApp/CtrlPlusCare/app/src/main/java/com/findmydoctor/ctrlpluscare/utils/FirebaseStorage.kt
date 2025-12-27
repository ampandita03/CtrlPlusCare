package com.findmydoctor.ctrlpluscare.utils

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

private const val TAG = "ImageUpload"

suspend fun uploadImageAndGetUrl(imageUri: Uri): String {
    Log.d(TAG, "uploadImageAndGetUrl started, uri=$imageUri")

    val storageRef = FirebaseStorage.getInstance()
        .reference
        .child("profile_images/${System.currentTimeMillis()}.jpg")

    try {
        Log.d(TAG, "Uploading image to path=${storageRef.path}")

        // Upload image
        storageRef.putFile(imageUri).await()
        Log.d(TAG, "Image upload successful")

        // Get download URL
        val downloadUrl = storageRef.downloadUrl.await().toString()
        Log.d(TAG, "Download URL fetched successfully: $downloadUrl")

        return downloadUrl
    } catch (e: Exception) {
        Log.e(TAG, "Image upload failed", e)
        throw e // important: rethrow so caller can handle failure
    }
}
