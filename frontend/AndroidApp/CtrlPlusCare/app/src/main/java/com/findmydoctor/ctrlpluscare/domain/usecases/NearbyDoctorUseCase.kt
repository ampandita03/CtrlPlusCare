package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.DoctorResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface

class NearbyDoctorUseCase(
    private val doctorsInterface: DoctorsInterface
) {
    suspend operator fun invoke(longitude: Double, latitude: Double): Result<DoctorResponse> {
        return doctorsInterface.doctorsNearby(longitude, latitude)
    }
}