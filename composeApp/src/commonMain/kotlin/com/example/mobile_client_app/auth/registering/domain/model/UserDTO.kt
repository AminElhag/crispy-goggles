package com.example.mobile_client_app.auth.registering.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class UserDTO(
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
) {

    @OptIn(ExperimentalTime::class)
    fun toRequest() = UserRequest(
        firstName = firstName,
        middleName = middleName,
        lastName = lastName,
        idNumber = idNumber,
        dataOfBirth = dataOfBirth?.toInstant(TimeZone.UTC)?.toLocalDateTime(TimeZone.UTC),
        sexId = sexId,
        phoneNumber = phoneNumber,
        email = email,
        password = password,
        emergencyContact = emergencyContact,
        hearAboutUsId = hearAboutUsId,
        occupation = occupation,
        medicalConditionsIds = medicalConditionsIds,
    )
}