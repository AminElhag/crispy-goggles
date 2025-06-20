package com.example.mobile_client_app.auth.login.data.local.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

interface UserAPI {
    suspend fun login(
        emailOrPhone: String,
        password: String
    ): Boolean
}

class UserAPIImpl(private val httpClient: HttpClient) : UserAPI {
    override suspend fun login(
        emailOrPhone: String,
        password: String
    ): Boolean {
        return try {
            val response = httpClient.post("http://192.168.105.48:8080/login") {
                setBody(mapOf("username" to emailOrPhone, "password" to password))
                contentType(ContentType.Application.Json)
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Network error: ${e.message}")
            false
        }
    }
}