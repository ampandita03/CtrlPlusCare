package com.findmydoctor.ctrlpluscare.domain.usecases

import com.findmydoctor.ctrlpluscare.data.dto.AppointmentsResponse
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface

class GetAppointmentsUseCase(
    private val doctorsInterface: PatientInterface
) {
    suspend operator fun invoke(): Result<AppointmentsResponse> {
        return doctorsInterface.getMyAppointments()
    }
}
class CancelAppointmentsUseCase(
    private val doctorsInterface: PatientInterface
) {
    suspend operator fun invoke(appointmentId: String): Result<Unit> {
        return doctorsInterface.cancelMyAppointment(appointmentId)
    }
}