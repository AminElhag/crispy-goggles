package com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.impl

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.AvailableTimeResponse
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.RequestAppointmentRequest
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.TrainingSpecializationResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.PersonalTrainerApi
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.PersonalTrainerRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

class PersonalTrainerRepositoryImpl(
    private val api: PersonalTrainerApi
) : PersonalTrainerRepository {
    override suspend fun getTrainers(): Result<List<TrainerResponse>, NetworkError> {
        return api.getTrainers()
    }

    override suspend fun getAvailableTime(
        trainerId: String,
        trainingTypeId: Int,
        selectedDate: LocalDate,
        selectedSpecializationTypeId: Int
    ): Result<List<AvailableTimeResponse>, NetworkError> {
        return api.getAvailableTime(
            trainerId = trainerId,
            trainingTypeId = trainingTypeId,
            selectedDate = selectedDate,
            selectedSpecializationTypeId= selectedSpecializationTypeId
        )
    }

    override suspend fun getTrainingSpecialization(
        trainerId: String,
        trainingTypeId: Int
    ): Result<List<TrainingSpecializationResponse>, NetworkError> {
        return api.getTrainingSpecialization(trainerId = trainerId, trainingTypeId = trainingTypeId)
    }
}