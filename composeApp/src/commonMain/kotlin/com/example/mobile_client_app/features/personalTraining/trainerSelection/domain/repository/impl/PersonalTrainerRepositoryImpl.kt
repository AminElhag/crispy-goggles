package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.impl

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.PersonalTrainerApi
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.PersonalTrainerRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class PersonalTrainerRepositoryImpl(
    private val api: PersonalTrainerApi
) : PersonalTrainerRepository {
    override suspend fun getTrainers(): Result<List<TrainerResponse>, NetworkError> {
       return api.getTrainers()
    }
}