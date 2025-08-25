package com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.impl

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.PersonalTrainerApi
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PersonalTrainerApiImpl(
    private val httpClient: HttpClient
) : PersonalTrainerApi {
    override suspend fun getTrainers(): Result<List<TrainerResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/personalTraining/trainers")
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
}