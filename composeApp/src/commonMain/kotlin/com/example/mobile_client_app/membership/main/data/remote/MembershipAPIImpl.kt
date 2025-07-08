package com.example.mobile_client_app.membership.main.data.remote

import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.network.*
import kotlinx.serialization.SerializationException
import saschpe.log4k.Log

class MembershipAPIImpl(
    private val httpClient: HttpClient
) : MembershipAPI {
    override suspend fun getMembership(token: String): Result<MembershipResponse, NetworkError> {
        val response = try {
            httpClient.get("http://192.168.113.17:8080/api/v1/client/memberships") {
                headers["Bearer"] = token
                contentType(ContentType.Application.Json)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            Log.error("Caught exception: ${e.message}", e)
            return Result.Error(NetworkError.UNKNOWN)
        }
        return when (response.status.value) {
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
        }

    }
}