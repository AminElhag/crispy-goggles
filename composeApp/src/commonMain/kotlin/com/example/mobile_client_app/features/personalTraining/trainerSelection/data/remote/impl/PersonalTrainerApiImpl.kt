package com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.impl

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.AvailableTimeResponse
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.RequestAppointmentRequest
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.TrainingSpecializationResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.PersonalTrainerApi
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate

class PersonalTrainerApiImpl(
    private val httpClient: HttpClient
) : PersonalTrainerApi {
    override suspend fun getTrainers(): Result<List<TrainerResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/personal-trainer/trainers")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<TrainerResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun getAvailableTime(
        trainerId: String,
        trainingTypeId: Int,
        selectedDate: LocalDate,
        selectedSpecializationTypeId: Int
    ): Result<List<AvailableTimeResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/personal-trainer/available-time") {
                parameter("trainer_id", trainerId)
                parameter("training_type_id", trainingTypeId)
                parameter("selected_date", selectedDate)
                parameter("selected_specialization_id", selectedSpecializationTypeId)
            }
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<AvailableTimeResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun getTrainingSpecialization(
        trainerId: String,
        trainingTypeId: Int?
    ): Result<List<TrainingSpecializationResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/personal-trainer/training-specialization") {
                parameter("trainer_id", trainerId)
                trainingTypeId?.let { parameter("training_type_id", trainingTypeId) }

            }
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<TrainingSpecializationResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }
}