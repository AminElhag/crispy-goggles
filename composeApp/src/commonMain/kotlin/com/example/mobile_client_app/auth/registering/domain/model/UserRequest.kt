package com.example.mobile_client_app.auth.registering.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val idNumber: String,
    val dataOfBirth: LocalDateTime?,
    val sexId: Int,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val emergencyContact: String,
    val hearAboutUsId: Int,
    val occupation: String,
    val medicalConditionsIds: List<Int>,
)
