package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.DoctorProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.PatientAppointmentResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdateDoctorProfileRequest
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface

class GetDoctorProfileUseCase(
    private val doctorsInterface: DoctorsInterface
) {
    suspend operator fun invoke(): Result<DoctorProfileResponse>{
        return doctorsInterface.getDoctorProfile()
    }
}

class GetDoctorPatientsUseCase(
    private val doctorsInterface: DoctorsInterface
){
    suspend operator fun invoke(): Result<PatientAppointmentResponse>{
        return doctorsInterface.getPatients()
    }
}
class UpdateDoctorProfileUseCase(
    private val doctorsInterface: DoctorsInterface
) {
    suspend operator fun invoke(updateDoctorProfileRequest: UpdateDoctorProfileRequest): Result<Unit>{
        return doctorsInterface.updateDoctorProfile(updateDoctorProfileRequest)
    }
}