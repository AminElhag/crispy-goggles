package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase

import com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.AppointmentRepository
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.RequestAppointmentRequest
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

class RequestAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(
        trainerId: String,
        selectedTrainingType: Int,
        selectedSpecializationType: Int,
        selectedDate: LocalDate,
        selectedTimeId: Int
    ): Result<Unit, NetworkError> {
        return repository.requestAppointment(
            RequestAppointmentRequest(
                trainerId = trainerId,
                selectedTrainingType = selectedTrainingType,
                selectedSpecializationType = selectedSpecializationType,
                selectedDate = selectedDate,
                selectedTimeId = selectedTimeId
            )
        )
    }
}
