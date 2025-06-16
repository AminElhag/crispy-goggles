package com.example.mobile_client_app.auth.login.data.local.api

import com.example.mobile_client_app.Constants
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface UserAPI {
    suspend fun login(
        emailOrPhone: String,
        password: String
    ): HttpResponse
}

class UserAPIImpl(private val httpClient: HttpClient) : UserAPI {
    override suspend fun login(
        emailOrPhone: String,
        password: String
    ): HttpResponse {
        val response = httpClient.post("${Constants.BASE_URL}auth/login") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("username" to emailOrPhone, "password" to password))
        }
        return response
    }
}