package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MembershipPlan(
    @SerialName("id")
    var id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("description")
    val description: String,
    @SerialName("commitment_period")
    val commitmentPeriod: String,
    @SerialName("payment_interval")
    val paymentInterval: String?,
    @SerialName("minimum_cancellation_period")
    val minimumCancellationPeriod: String?,
    @SerialName("max_cancellation_period")
    val maxCancellationPeriod: String?,
    @SerialName("limit_number_of_visits")
    val limitNumberOfVisits: Int?,
    @SerialName("can_be_renew")
    val canBeRenew: Boolean,
    @SerialName("agreements")
    val agreements: List<AgreementResponse>?,
)