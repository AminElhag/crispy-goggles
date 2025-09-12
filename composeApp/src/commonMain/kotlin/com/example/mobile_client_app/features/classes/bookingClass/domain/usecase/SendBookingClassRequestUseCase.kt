package com.example.mobile_client_app.features.classes.bookingClass.domain.usecase

import com.example.mobile_client_app.features.classes.bookingClass.domain.repository.ClassRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class SendBookingClassRequestUseCase(
    private val repository: ClassRepository
) {
    suspend operator fun invoke(
        classId: Long
    ): Result<Unit, NetworkError> {
        return repository.sendBookingClassRequest(classId)
    }
}
