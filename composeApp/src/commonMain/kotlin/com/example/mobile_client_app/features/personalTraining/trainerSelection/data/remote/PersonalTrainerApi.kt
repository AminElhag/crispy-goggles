package com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface PersonalTrainerApi {
    suspend fun getTrainers(): Result<List<TrainerResponse>, NetworkError>
}
