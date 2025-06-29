package com.example.mobile_client_app.auth.registering.data.remote

import com.example.mobile_client_app.auth.registering.domain.model.CreateUserResponse
import com.example.mobile_client_app.auth.registering.domain.model.UserRequest
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.network.*
import kotlinx.serialization.SerializationException

class RegisteringAPIImpl(
    private val httpClient: HttpClient
) : RegisteringAPI {
    override suspend fun registerUser(userRequest: UserRequest): Result<CreateUserResponse, NetworkError> {
        val response = try {
            httpClient.post("http://192.168.105.48:8080/login") {
                setBody(userRequest)
                contentType(ContentType.Application.Json)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UNKNOWN)
        }
        return when (response.status.value) {
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
        }
    }
}