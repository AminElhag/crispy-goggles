package com.example.mobile_client_app.features.onboarding.classes.data.remote

import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface ClassesApi {
    suspend fun getClasses(): Result<List<FitnessClassResponse>, NetworkError>
}
