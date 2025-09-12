package com.example.mobile_client_app.features.classes.bookingClass.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.classes.bookingClass.data.models.ClassDetailsResponse
import com.example.mobile_client_app.features.classes.bookingClass.domain.usecase.GetClassDetailsUseCase
import com.example.mobile_client_app.features.classes.bookingClass.domain.usecase.SendBookingClassRequestUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BookingClassScreenUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val date: ClassDetailsResponse? = null,
    val isBookingClassRequest: Boolean = false,
    val bookingClassRequestError: String? = null,
    val isBookingClassRequestComparable: Boolean = false,
)

class BookingClassViewModel(
    private val getClassDetailsUseCase: GetClassDetailsUseCase,
    private val sendBookingClassRequestUseCase: SendBookingClassRequestUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingClassScreenUiState())
    val uiState: StateFlow<BookingClassScreenUiState> = _uiState.asStateFlow()

    var classId = mutableStateOf<Long?>(null)
        private set

    fun getClassDetails() {
        if (classId.value == null) return
        _uiState.value = uiState.value.copy(isLoading = true, error = null, date = null)
        viewModelScope.launch {
            getClassDetailsUseCase.invoke(classId.value!!).onError { error ->
                _uiState.value = uiState.value.copy(isLoading = false, error = error.displayMessage)
            }.onSuccess { classDetails ->
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    date = classDetails
                )
            }
        }
    }

    fun onRefresh() {
        getClassDetails()
    }

    fun setClassId(classId: Long) {
        this.classId.value = classId
        getClassDetails()
    }

    fun sendBookingRequest() {
        if (classId.value == null) return
        _uiState.value = _uiState.value.copy(
            isBookingClassRequest = true,
            isBookingClassRequestComparable = false,
            bookingClassRequestError = null
        )
        viewModelScope.launch {
            sendBookingClassRequestUseCase(classId.value!!)
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        isBookingClassRequest = false,
                        bookingClassRequestError = error.displayMessage
                    )
                }.onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        isBookingClassRequest = false,
                        isBookingClassRequestComparable = true
                    )
                }
        }
    }

}