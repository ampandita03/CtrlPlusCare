package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse
import com.findmydoctor.ctrlpluscare.data.dto.UpdatePatientProfile
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface

class PatientProfileUseCase(
    private val patientInterface: PatientInterface
) {
    suspend operator fun invoke(): Result<PatientProfileResponse>{
        return patientInterface.getPatientProfile()
    }
}
class UpdatePatientProfileUseCase(
    private val patientInterface: PatientInterface
) {
    suspend operator fun invoke(updatePatientProfile: UpdatePatientProfile): Result<Unit>{
        return patientInterface.updatePatientProfile(updatePatientProfile)
    }
}