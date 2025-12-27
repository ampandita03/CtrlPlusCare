package com.findmydoctor.ctrlpluscare.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices


@SuppressLint("MissingPermission")
fun fetchUserLocation(
    context: Context,
    onLocationFetched: (lat: Double, lng: Double) -> Unit
) {
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    fusedClient.lastLocation
        .addOnSuccessListener { location ->
            location?.let {
                onLocationFetched(it.latitude, it.longitude)
            }
        }
}
