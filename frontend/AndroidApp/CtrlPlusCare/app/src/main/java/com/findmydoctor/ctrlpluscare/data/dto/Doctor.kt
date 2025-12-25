package com.findmydoctor.ctrlpluscare.data.dto

data class Doctor(
    val name : String,
    val speciality : String,
    val location : Location,
    val hospital : String,
    val fees : String,
    val rating : Double,
    val photo : String,
    val experience : String,
    val description : String
)

data class Location(
    val latitude : Double,
    val longitude : Double,
)

