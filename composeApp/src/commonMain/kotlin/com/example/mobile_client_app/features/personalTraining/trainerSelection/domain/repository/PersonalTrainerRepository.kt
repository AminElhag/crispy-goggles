package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface PersonalTrainerRepository {
    suspend fun getTrainers(): Result<List<TrainerResponse>, NetworkError>
}
