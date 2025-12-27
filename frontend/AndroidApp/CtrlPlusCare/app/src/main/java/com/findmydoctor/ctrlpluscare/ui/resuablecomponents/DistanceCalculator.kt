package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import android.location.Location

fun calculateDistance(
    point1 : List<Double>,
    point2: List<Double>
): Float {
    val results = FloatArray(1)
    Location.distanceBetween(
        point1[1], point1[0],
        point2[1], point2[0],
        results
    )

    val distanceInMeters = results[0]

    return distanceInMeters
}