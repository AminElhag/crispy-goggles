package com.example.mobile_client_app.features.membership.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.component.toDDMMYYY
import com.example.mobile_client_app.features.membership.main.domain.model.ContractOption
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipPlan
import com.example.mobile_client_app.features.membership.main.domain.useCase.CheckPromoCodeUseCase
import com.example.mobile_client_app.features.membership.main.domain.useCase.CheckoutInitUseCase
import com.example.mobile_client_app.features.membership.main.domain.useCase.GetMembershipUseCase
import com.example.mobile_client_app.util.network.checkInternetConnection
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import saschpe.log4k.Log
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Error(val message: String) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}

sealed class MembershipEvent {
    object Reset : MembershipEvent()
    data class ShowSnackbar(val message: String) : MembershipEvent()
}

class MembershipViewModel(
    private val getMembershipUseCase: GetMembershipUseCase,
    private val checkPromoCodeUseCase: CheckPromoCodeUseCase,
    private val checkoutInitUseCase: CheckoutInitUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState<MembershipResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<MembershipResponse>> = _uiState

    private val _events = MutableStateFlow<MembershipEvent>(MembershipEvent.Reset)
    val events: StateFlow<MembershipEvent> = _events

    var isConnected = false

    var promoCode by mutableStateOf("")
        private set
    var plans by mutableStateOf<MembershipResponse?>(null)
        private set
    var selectedPlan by mutableStateOf<MembershipPlan?>(null)
        private set
    var selectedContractOption by mutableStateOf<ContractOption?>(null)
        private set

    @OptIn(ExperimentalTime::class)
    var today = Clock.System.now().toEpochMilliseconds()
        private set

    @OptIn(ExperimentalTime::class)
    var maxDate = Clock.System.now().plus(duration = 7.days).toEpochMilliseconds()
        private set


    init {
        fetchMembershipPlans()
    }

    fun fetchMembershipPlans() {
        _uiState.value = UiState.Loading
        isConnected = checkInternetConnection(viewModelScope)
        if (isConnected) {
            CoroutineScope(Dispatchers.Default).launch {
                getMembershipUseCase.invoke().onSuccess { response ->
                    plans = response
                    _uiState.value = UiState.Success(response)
                }.onError {error ->
                    _uiState.value = UiState.Error(error.displayMessage)
                }
            }
        } else {
            _uiState.value = UiState.Error("Failed to load data. Please check your connection.")
        }
    }

    var showDatePicker by mutableStateOf(false)
        private set

    var startDate by mutableStateOf<LocalDateTime?>(null)
        private set

    fun updateShowDatePicker(isShowDatePicker: Boolean) {
        showDatePicker = isShowDatePicker
    }

    fun updateDateOfBirth(newDate: Long) {
        startDate = millisToDate(millis = newDate)
    }

    fun getSelectDateAsString(): String? {
        return startDate.toDDMMYYY()
    }

    fun updatePromoCode(newPromoCode: String) {
        if (newPromoCode.length <= 8) promoCode = newPromoCode
    }

    fun updateSelectedPlan(newPlan: MembershipPlan) {
        selectedPlan = newPlan
    }

    fun isContractOptionSelected(
        contractOption: ContractOption,
    ): Boolean {
        return selectedContractOption?.id == contractOption.id
    }

    fun selectContractOption(contractOption: ContractOption) {
        selectedContractOption = contractOption
    }

    fun onCheckInfo() {
        if (selectedPlan == null) {
            Log.debug { "Selected plan is null" }
            _events.value = MembershipEvent.ShowSnackbar("Select your plan")
        } else if (startDate == null) {
            _events.value = MembershipEvent.ShowSnackbar("You need to select the day that you want to start on it")
        } else if (promoCode.isNotBlank()) {
            if (promoCode.length != 8) {
                _events.value = MembershipEvent.ShowSnackbar("Promo code must be 8 digits")
            }else{
                viewModelScope.launch {
                    checkPromoCodeUseCase.invoke(promoCode).onSuccess { response ->
                        sendCompleteRequest()
                    }.onError {error ->
                        _events.value = MembershipEvent.ShowSnackbar(error.displayMessage)
                    }
                }
            }
        } else {
            sendCompleteRequest()
        }
    }

    fun sendCompleteRequest() {

    }

    fun resetEvent() {

    }
}