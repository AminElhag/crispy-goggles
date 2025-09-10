package com.example.mobile_client_app.features.auth.login.data.remote.api

import com.example.mobile_client_app.features.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class LoginAPIImpl(private val httpClient: HttpClient) : LoginAPI {
    override suspend fun login(
        emailOrPhone: String,
        password: String
    ): Result<LoginResponse, NetworkError> {
        val response = try {
            httpClient.post("/api/auth/login") {
                setBody(mapOf("username" to emailOrPhone, "password" to password))
            }
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<LoginResponse>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())

        }
    }
}