package com.example.mobile_client_app.auth.login.data.local.api

import com.example.mobile_client_app.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.network.*
import kotlinx.serialization.SerializationException

interface UserAPI {
    suspend fun login(
        emailOrPhone: String,
        password: String
    ): Result<LoginResponse, NetworkError>
}

class UserAPIImpl(private val httpClient: HttpClient) : UserAPI {
    override suspend fun login(
        emailOrPhone: String,
        password: String
    ): Result<LoginResponse, NetworkError> {
        val response = try {
            httpClient.post("http://192.168.105.48:8080/login") {
                setBody(mapOf("username" to emailOrPhone, "password" to password))
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
                val response = response.body<LoginResponse>()
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