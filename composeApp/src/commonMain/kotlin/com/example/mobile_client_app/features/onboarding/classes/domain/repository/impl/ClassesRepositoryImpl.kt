package com.example.mobile_client_app.features.onboarding.classes.domain.repository.impl

import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.features.onboarding.classes.data.remote.ClassesApi
import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass
import com.example.mobile_client_app.features.onboarding.classes.domain.repository.ClassesRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.map

class ClassesRepositoryImpl(
    private val classesApi: ClassesApi
) : ClassesRepository {
    override suspend fun getClasses(): Result<List<FitnessClassResponse>, NetworkError> {
        return classesApi.getClasses()
    }
}