package com.example.mobile_client_app.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanResponse(
    @SerialName("id")
    var id: String,
    @SerialName("title")
    val title: String,
    @SerialName("price")
    val price: String,
    @SerialName("features")
    val features: List<String>
)