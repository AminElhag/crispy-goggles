package com.example.mobile_client_app.util.network

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}

fun networkError(
    error: NetworkError,
): String = when (error) {
    NetworkError.REQUEST_TIMEOUT -> "Request timeout"
    NetworkError.UNAUTHORIZED -> "Unauthorized"
    NetworkError.CONFLICT -> "Conflict"
    NetworkError.TOO_MANY_REQUESTS -> "Too many requests"
    NetworkError.NO_INTERNET -> "No internet"
    NetworkError.PAYLOAD_TOO_LARGE -> "Payment too large"
    NetworkError.SERVER_ERROR -> "Server error"
    NetworkError.SERIALIZATION -> "Serialization error"
    NetworkError.UNKNOWN -> "Unknown error"
}