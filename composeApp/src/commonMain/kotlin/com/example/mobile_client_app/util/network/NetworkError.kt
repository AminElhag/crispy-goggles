package com.example.mobile_client_app.util.network

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.io.IOException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

sealed class NetworkError(
    val displayMessage: String,
    val httpCode: Int? = null,
    val originalException: Throwable? = null
) : Exception(displayMessage, originalException) {
    // Client errors (4xx)
    object Unauthorized : NetworkError("Session expired", 401)
    object Forbidden : NetworkError("Access denied", 403)
    data class NotFound(val serverMessage: String?) : NetworkError(serverMessage ?: "Resource not found", 404)
    object Conflict : NetworkError("Data conflict", 409)
    object PayloadTooLarge : NetworkError("File too large", 413)
    object TooManyRequests : NetworkError("Too many attempts", 429)

    // Server errors (5xx)
    object InternalServerError : NetworkError("Server error", 500)
    object BadGateway : NetworkError("Bad gateway", 502)
    object ServiceUnavailable : NetworkError("Service unavailable", 503)

    // Network issues
    object RequestTimeout : NetworkError("Request timed out")
    object NoInternet : NetworkError("No internet connection")

    // Custom/unknown errors
    class CustomError(
        message: String,
        code: Int? = null,
        e: Throwable? = null
    ) : NetworkError(message, code, e)

    class UnknownError(e: Throwable?) :
        NetworkError("Unknown error: ${e?.message}", null, e)
}


suspend fun HttpResponse.toException(): NetworkError {
    val status = this.status
    val body = try {
        this.body<ErrorResponse>().message
    } catch (e: Exception) {
        "Unable to read response body: ${e.message}"
    }

    return when (status.value) {
        in 400..499 -> handleClientError(status, body)
        in 500..599 -> handleServerError(status, body)
        else -> NetworkError.CustomError(
            "Unexpected status $status: $body",
            status.value
        )
    }
}

private fun handleClientError(status: HttpStatusCode, body: String?): NetworkError {
    return when (status) {
        HttpStatusCode.Unauthorized -> NetworkError.Unauthorized
        HttpStatusCode.Forbidden -> NetworkError.Forbidden
        HttpStatusCode.NotFound -> NetworkError.NotFound(body)
        HttpStatusCode.Conflict -> NetworkError.Conflict
        HttpStatusCode.PayloadTooLarge -> NetworkError.PayloadTooLarge
        HttpStatusCode.TooManyRequests -> NetworkError.TooManyRequests
        else -> parseCustomError(status, body)
    }
}

private fun handleServerError(status: HttpStatusCode, body: String?): NetworkError {
    return when (status) {
        HttpStatusCode.InternalServerError -> NetworkError.InternalServerError
        HttpStatusCode.BadGateway -> NetworkError.BadGateway
        HttpStatusCode.ServiceUnavailable -> NetworkError.ServiceUnavailable
        else -> NetworkError.CustomError(
            "Server error ${status.value}: $body",
            status.value
        )
    }
}

private fun parseCustomError(status: HttpStatusCode, body: String?): NetworkError {
    return try {
        // Parse backend-specific error format
        val errorResponse = Json.decodeFromString<ErrorResponse>(body ?: "")
        NetworkError.CustomError(
            errorResponse.message ?: "Error ${status.value}",
            status.value
        )
    } catch (e: Exception) {
        // Fallback if parsing fails
        NetworkError.CustomError(
            "Error ${status.value}: ${body?.take(200)}",
            status.value
        )
    }
}

// Backend error response format
@Serializable
data class ErrorResponse(
    val status: Int? = null,
    val message: String? = null,
    val error: String,
    val timestamp: String,
)

suspend fun convertToNetworkError(e: Exception): NetworkError {
    return when (e) {
        is NetworkError -> e // Already our custom error
        is ClientRequestException -> e.response.toException()
        is ServerResponseException -> e.response.toException()
        is HttpRequestTimeoutException -> NetworkError.RequestTimeout
        is IOException -> NetworkError.NoInternet
        is SerializationException -> NetworkError.CustomError("Data parsing error")
        else -> NetworkError.UnknownError(e)
    }
}