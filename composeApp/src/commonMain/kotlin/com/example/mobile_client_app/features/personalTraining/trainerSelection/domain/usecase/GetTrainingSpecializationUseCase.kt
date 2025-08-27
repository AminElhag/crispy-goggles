package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase

import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.TrainingSpecializationResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.PersonalTrainerRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetTrainingSpecializationUseCase(
    private val repository: PersonalTrainerRepository
) {
    suspend operator fun invoke(
        trainerId: String,
        trainingTypeId: Int
    ): Result<List<TrainingSpecializationResponse>, NetworkError> {
        return repository.getTrainingSpecialization(
            trainerId = trainerId,
            trainingTypeId = trainingTypeId
        )
    }
}
