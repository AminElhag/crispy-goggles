package com.example.mobile_client_app.features.onboarding.classes.data.remote

import com.example.mobile_client_app.features.classes.bookingClass.data.models.ClassDetailsResponse
import com.example.mobile_client_app.features.onboarding.classes.data.model.BookClassRequest
import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ClassesApiImpl(
    private val httpClient: HttpClient
) : ClassesApi {
    override suspend fun getClasses(): Result<List<FitnessClassResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/classes")
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

    override suspend fun getClassDetails(classId: Long): Result<ClassDetailsResponse, NetworkError> {
        val response = try {
            httpClient.get("/api/classes/$classId/details"){

            }
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<ClassDetailsResponse>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun sendBookingClassRequest(classId: Long): Result<Unit, NetworkError> {
        val response = try {
            httpClient.post("/api/classes/book"){
                setBody(BookClassRequest(classId))
            }
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<Unit>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }
}