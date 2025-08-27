package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase

import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.AvailableTimeResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.TrainingSpecializationResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.PersonalTrainerRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

class GetAvailableTimeUseCase(
    private val repository: PersonalTrainerRepository
) {
    suspend operator fun invoke(
        trainerId: String,
        trainingTypeId: Int,
        selectedDate: LocalDate,
        selectedSpecializationTypeId: Int
    ): Result<List<AvailableTimeResponse>, NetworkError> {
        return repository.getAvailableTime(
            trainerId = trainerId,
            trainingTypeId = trainingTypeId,
            selectedDate = selectedDate,
            selectedSpecializationTypeId = selectedSpecializationTypeId
        )
    }
}
