package com.example.mobile_client_app.features.onboarding.home.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("instructor")
    val instructor: String,
    @SerialName("image_url")
    val imageUrl: String,
)
