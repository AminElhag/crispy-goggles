package com.example.mobile_client_app.features.personalTraining.appointments.data.remote.impl

import com.example.backend.mobileClient.features.appointment.controller.models.AppointmentResponse
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.CancelAppointmentRequest
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment
import com.example.mobile_client_app.features.personalTraining.appointments.data.remote.AppointmentApi
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AppointmentApiImpl(
    private val httpClient: HttpClient
) : AppointmentApi {
    override suspend fun getAppointments(): Result<List<AppointmentResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/appointment")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<AppointmentResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun cancelAppointment(request: CancelAppointmentRequest): Result<Unit, NetworkError> {
        val response = try {
            httpClient.post("/api/v1/appointment/cancel"){
                setBody(request)
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