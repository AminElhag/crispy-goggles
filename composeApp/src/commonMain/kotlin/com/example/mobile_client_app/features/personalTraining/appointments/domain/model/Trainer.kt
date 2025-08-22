package com.example.mobile_client_app.features.personalTraining.appointments.domain.model

data class Trainer(
    val id: String,
    val name: String,
    val avatar: String,
    val specialty: String = ""
)