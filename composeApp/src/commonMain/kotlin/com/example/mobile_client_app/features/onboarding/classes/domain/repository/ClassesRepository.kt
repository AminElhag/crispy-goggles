package com.example.mobile_client_app.features.onboarding.classes.domain.repository

import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface ClassesRepository {
    suspend fun getClasses(): Result<List<FitnessClassResponse>, NetworkError>
}
