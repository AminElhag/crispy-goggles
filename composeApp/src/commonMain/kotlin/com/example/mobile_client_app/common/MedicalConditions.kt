package com.example.mobile_client_app.common

import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.asthma
import mobile_client_app.composeapp.generated.resources.cardiovascular_diseases
import mobile_client_app.composeapp.generated.resources.depression
import mobile_client_app.composeapp.generated.resources.epilepsy
import mobile_client_app.composeapp.generated.resources.fibromyalgia
import mobile_client_app.composeapp.generated.resources.hypertension
import mobile_client_app.composeapp.generated.resources.hypothyroidism
import mobile_client_app.composeapp.generated.resources.insulin_resistance
import mobile_client_app.composeapp.generated.resources.low_blood_pressure_hypotension
import mobile_client_app.composeapp.generated.resources.multiple_Sclerosis_ms
import mobile_client_app.composeapp.generated.resources.osteoporosis
import mobile_client_app.composeapp.generated.resources.type_1_diabetes
import org.jetbrains.compose.resources.StringResource

val medicalCondition = mutableListOf(
    MedicalCondition(Res.string.hypertension, 1),
    MedicalCondition(Res.string.asthma, 2),
    MedicalCondition(Res.string.cardiovascular_diseases, 3),
    MedicalCondition(Res.string.type_1_diabetes, 4),
    MedicalCondition(Res.string.epilepsy, 5),
    MedicalCondition(Res.string.hypothyroidism, 6),
    MedicalCondition(Res.string.osteoporosis, 7),
    MedicalCondition(Res.string.insulin_resistance, 8),
    MedicalCondition(Res.string.multiple_Sclerosis_ms, 9),
    MedicalCondition(Res.string.fibromyalgia, 10),
    MedicalCondition(Res.string.low_blood_pressure_hypotension, 10),
    MedicalCondition(Res.string.depression, 10),
)

data class MedicalCondition(
    val name: StringResource,
    val id: Int
)