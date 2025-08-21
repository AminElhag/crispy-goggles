package com.example.mobile_client_app.features.onboarding.classes.data.remote

import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ClassesApiImpl(
    private val httpClient: HttpClient
) : ClassesApi {
    override suspend fun getClasses(): Result<List<FitnessClassResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/classes/timeTable")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<FitnessClassResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }

    }
}