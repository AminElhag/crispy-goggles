package com.example.mobile_client_app.features.auth.profile.data.remote

import com.example.mobile_client_app.features.auth.profile.data.remote.models.FreezingRequest
import com.example.mobile_client_app.features.auth.profile.data.remote.models.ProfileResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ProfileAPIImpl(
    private val httpClient: HttpClient
) : ProfileAPI {
    override suspend fun getProfile(): Result<ProfileResponse, NetworkError> {
        val response = try {
            httpClient.get("/api/profile")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<ProfileResponse>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())

        }
    }

    override suspend fun sendFreezingRequest(freezingRequest: FreezingRequest): Result<Unit, NetworkError> {
        val response = try {
            httpClient.post("/api/membership/freeze") {
                setBody(freezingRequest)
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