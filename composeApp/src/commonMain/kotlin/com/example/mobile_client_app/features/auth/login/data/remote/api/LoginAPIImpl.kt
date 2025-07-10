package com.example.mobile_client_app.features.auth.login.data.remote.api

import com.example.mobile_client_app.features.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import com.example.mobile_client_app.util.network.safeApiCall
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class LoginAPIImpl(private val httpClient: HttpClient) : LoginAPI {
    override suspend fun login(
        emailOrPhone: String,
        password: String
    ): Result<LoginResponse, NetworkError> {
        return safeApiCall {
            httpClient.post("/login") {
                setBody(mapOf("username" to emailOrPhone, "password" to password))
                contentType(ContentType.Application.Json)
            }.body<LoginResponse>()
        }.onSuccess {
            return Result.Success(it)
        }.onError {
            return Result.Error(it)
        }
        /*val response = try {
            httpClient.post("http://192.168.105.48:8080/login") {
                setBody(mapOf("username" to emailOrPhone, "password" to password))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            return com.example.mobile_client_app.util.network.Result.Error(e.toNetworkError())
        }
        return Result.Success(response.body())*/
        /*return when (response.status.value) {
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
        }*/
    }
}