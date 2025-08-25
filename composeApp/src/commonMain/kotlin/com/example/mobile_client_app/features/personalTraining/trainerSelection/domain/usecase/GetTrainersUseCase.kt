package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.PersonalTrainerRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetTrainersUseCase(
    private val repository: PersonalTrainerRepository
) {
    suspend operator fun invoke(): Result<List<TrainerResponse>, NetworkError> {
        return repository.getTrainers()
    }
}
