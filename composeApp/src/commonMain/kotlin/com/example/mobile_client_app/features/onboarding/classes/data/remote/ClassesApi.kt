package com.example.mobile_client_app.features.onboarding.classes.data.remote

import com.example.mobile_client_app.features.classes.bookingClass.data.models.ClassDetailsResponse
import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface ClassesApi {
    suspend fun getClasses(): Result<List<FitnessClassResponse>, NetworkError>
    suspend fun getClassDetails(classId: Long): Result<ClassDetailsResponse, NetworkError>
    suspend fun sendBookingClassRequest(classId: Long): Result<Unit, NetworkError>
}
