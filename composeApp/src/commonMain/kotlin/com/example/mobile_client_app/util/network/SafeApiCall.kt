package com.example.mobile_client_app.util.network

suspend fun <T> safeApiCall(block: suspend () -> T): Result<T, NetworkError> {
    return try {
       Result.Success(block())
    } catch (e: Exception) {
        Result.Error(convertToNetworkError(e))
    }
}