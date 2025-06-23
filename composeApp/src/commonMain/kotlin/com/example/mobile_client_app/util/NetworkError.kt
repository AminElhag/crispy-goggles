package com.example.mobile_client_app.util

import com.example.mobile_client_app.util.NetworkError.CONFLICT
import com.example.mobile_client_app.util.NetworkError.NO_INTERNET
import com.example.mobile_client_app.util.NetworkError.PAYLOAD_TOO_LARGE
import com.example.mobile_client_app.util.NetworkError.REQUEST_TIMEOUT
import com.example.mobile_client_app.util.NetworkError.SERIALIZATION
import com.example.mobile_client_app.util.NetworkError.SERVER_ERROR
import com.example.mobile_client_app.util.NetworkError.TOO_MANY_REQUESTS
import com.example.mobile_client_app.util.NetworkError.UNAUTHORIZED
import com.example.mobile_client_app.util.NetworkError.UNKNOWN

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
    REQUEST_TIMEOUT -> "Request timeout"
    UNAUTHORIZED -> "Unauthorized"
    CONFLICT -> "Conflict"
    TOO_MANY_REQUESTS -> "Too many requests"
    NO_INTERNET -> "No internet"
    PAYLOAD_TOO_LARGE -> "Payment too large"
    SERVER_ERROR -> "Server error"
    SERIALIZATION -> "Serialization error"
    UNKNOWN -> "Unknown error"
}