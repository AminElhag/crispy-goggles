package com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.AvailableTimeResponse
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.RequestAppointmentRequest
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.TrainingSpecializationResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

interface PersonalTrainerApi {
    suspend fun  getTrainers(): Result<List<TrainerResponse>, NetworkError>
    suspend fun getTrainingSpecialization(
        trainerId: String,
        trainingTypeId: Int?
    ): Result<List<TrainingSpecializationResponse>, NetworkError>

    suspend fun getAvailableTime(
        trainerId: String,
        trainingTypeId: Int,
        selectedDate: LocalDate,
        selectedSpecializationTypeId: Int
    ): Result<List<AvailableTimeResponse>, NetworkError>

}
