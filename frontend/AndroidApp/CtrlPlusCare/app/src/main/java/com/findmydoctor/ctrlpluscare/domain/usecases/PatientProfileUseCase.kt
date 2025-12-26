package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface

class PatientProfileUseCase(
    private val patientInterface: PatientInterface
) {
    suspend operator fun invoke(): Result<PatientProfileResponse>{
        return patientInterface.getPatientProfile()
    }
}