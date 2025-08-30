package com.example.mobile_client_app.features.classes.bookingClass.domain.repository

import com.example.mobile_client_app.features.classes.bookingClass.data.models.ClassDetailsResponse
import com.example.mobile_client_app.features.onboarding.classes.data.remote.ClassesApi
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class ClassRepositoryImpl(
    private val api: ClassesApi
) : ClassRepository {
    override suspend fun getClassDetails(classId: Int): Result<ClassDetailsResponse, NetworkError> {
        return api.getClassDetails(classId=classId)
    }

    override suspend fun sendBookingClassRequest(classId: Int): Result<Unit, NetworkError> {
        return api.sendBookingClassRequest(classId)
    }
}