package com.example.mobile_client_app.features.auth.registering.data.remote

import com.example.mobile_client_app.features.auth.registering.domain.model.CreateUserResponse
import com.example.mobile_client_app.features.auth.registering.domain.model.UserRequest
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import com.example.mobile_client_app.util.network.safeApiCall
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RegisteringAPIImpl(
    private val httpClient: HttpClient
) : RegisteringAPI {
    override suspend fun registerUser(userRequest: UserRequest): Result<CreateUserResponse, NetworkError> {
        return safeApiCall {
            httpClient.post("/api/v1/client/register") {
                setBody(userRequest)
            }.body<CreateUserResponse>()
        }.onSuccess { response ->
            return Result.Success(response)
        }.onError { error ->
            return Result.Error(error)
        }
        /*val response = try {
            httpClient.post("/api/v1/client/register") {
                setBody(userRequest)
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            return Result.Error(e.toNetworkError())
        }
        return Result.Success(response.body())*/
        /*return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<CreateUserResponse>()
                Result.Success(response)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }*/
    }
}