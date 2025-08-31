package com.example.mobile_client_app.features.auth.registering.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
data class UserDTO(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val idNumber: String,
    val dataOfBirth: LocalDate? = null,
    val genderId: Int,
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
        dataOfBirth = dataOfBirth,
        genderId = genderId,
        phoneNumber = phoneNumber,
        email = email,
        password = password,
        emergencyContact = emergencyContact,
        hearAboutUsId = hearAboutUsId,
        occupation = occupation,
        medicalConditionsIds = medicalConditionsIds,
    )
}