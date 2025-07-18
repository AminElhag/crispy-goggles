package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckoutInitRequest(
    @SerialName("membership_id")
    val membershipId: Long,
    @SerialName("start_date")
    val startDate: LocalDate,
    @SerialName("promo_code")
    val promoCode: String?,
    @SerialName("accept_agreements_ids")
    val acceptAgreementsIds: Set<Long>?
)
