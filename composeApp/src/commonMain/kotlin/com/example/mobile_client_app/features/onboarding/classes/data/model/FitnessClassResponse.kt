package com.example.mobile_client_app.features.onboarding.classes.data.model

import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass
import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass.ClassDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FitnessClassResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("time")
    val time: String,
    @SerialName("trainer")
    val trainer: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("date")
    val date: Int
) {
    fun toDto(): FitnessClass {
        return FitnessClass(id, name, time, trainer, imageUrl, ClassDate.getById(date) ?: ClassDate.TODAY)
    }
}
