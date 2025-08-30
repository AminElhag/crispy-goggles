package com.example.mobile_client_app.features.classes.bookingClass.domain.repository

import com.example.mobile_client_app.features.classes.bookingClass.data.models.ClassDetailsResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface ClassRepository {
    suspend fun getClassDetails(classId: Int): Result<ClassDetailsResponse, NetworkError>
    suspend fun sendBookingClassRequest(classId: Int): Result<Unit, NetworkError>
}
