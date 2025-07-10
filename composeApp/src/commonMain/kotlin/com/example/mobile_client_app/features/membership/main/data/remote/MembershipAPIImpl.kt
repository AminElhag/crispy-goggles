package com.example.mobile_client_app.features.membership.main.data.remote

import com.example.mobile_client_app.features.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import com.example.mobile_client_app.util.network.safeApiCall
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MembershipAPIImpl(
    private val httpClient: HttpClient
) : MembershipAPI {

    override suspend fun getMembership(): Result<MembershipResponse, NetworkError> {
        return safeApiCall {
            httpClient.get("/api/v1/membership") {

            }.body<MembershipResponse>()
        }.onSuccess { response ->
            Result.Success(response)
        }.onError {error ->
            Result.Error(error)
        }
        /*val response = try {
            httpClient.get("/api/v1/membership") {
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            return Result.Error(e.toNetworkError())
        }
        return Result.Success(response.body())*/
        /*return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<MembershipResponse>()
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

    override suspend fun checkPromoCode(promoCode: String): Result<CheckPromoCodeResponse, NetworkError> {
        return safeApiCall {
            httpClient.get("/api/v1/promocode/check") {
                parameter("promo_code", promoCode)
            }.body<CheckPromoCodeResponse>()
        }.onSuccess { response ->
            Result.Success(response)
        }.onError {error ->
            Result.Error(error)
        }
        /*val response = try {
            httpClient.get("/api/v1/promocode/check") {
                parameter("promo_code", promoCode)
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            return Result.Error(e.toNetworkError())
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<CheckPromoCodeResponse>()
                Result.Success(response)
            }
            else -> {
                Result.Error(response.body())
            }*/

        /*401 -> Result.Error(NetworkError.UNAUTHORIZED)
        409 -> Result.Error(NetworkError.CONFLICT)
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)*/
    }
}
