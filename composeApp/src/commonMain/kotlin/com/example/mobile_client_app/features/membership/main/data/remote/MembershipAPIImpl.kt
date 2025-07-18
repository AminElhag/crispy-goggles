package com.example.mobile_client_app.features.membership.main.data.remote

import com.example.mobile_client_app.features.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitRequest
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate

class MembershipAPIImpl(
    private val httpClient: HttpClient
) : MembershipAPI {

    override suspend fun getMembership(): Result<MembershipResponse, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/membership")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<MembershipResponse>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }

    }

    override suspend fun checkoutInit(
        request: CheckoutInitRequest,
    ): Result<CheckoutInitResponse, NetworkError> {
        val response = try {
            httpClient.post("/api/v1/contract/checkout") {
                setBody(request)
            }
        } catch (e: NetworkError) {
            return Result.Error(e)

        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<CheckoutInitResponse>()
                Result.Success(response)
            }

            else -> {
                Result.Error(response.toException())
            }

        }

    }

    override suspend fun checkPromoCode(promoCode: String, paymentPlanID: Long): Result<CheckPromoCodeResponse, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/discounts/validate") {
                parameter("code", promoCode)
                parameter("payment_plan_id", paymentPlanID)
            }
        } catch (e: NetworkError) {
            return Result.Error(e)

        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<CheckPromoCodeResponse>()
                Result.Success(response)
            }

            else -> {
                Result.Error(response.toException())
            }

        }
    }
}