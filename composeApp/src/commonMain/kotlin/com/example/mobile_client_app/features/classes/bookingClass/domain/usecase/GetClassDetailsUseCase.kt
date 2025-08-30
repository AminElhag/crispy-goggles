package com.example.mobile_client_app.features.classes.bookingClass.domain.usecase

import com.example.mobile_client_app.features.classes.bookingClass.data.models.ClassDetailsResponse
import com.example.mobile_client_app.features.classes.bookingClass.domain.repository.ClassRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetClassDetailsUseCase(
    private val repository: ClassRepository
) {
    suspend operator fun invoke(
        classId: Int,
    ): Result<ClassDetailsResponse, NetworkError> {
        return repository.getClassDetails(
            classId = classId,
        )
    }
}
