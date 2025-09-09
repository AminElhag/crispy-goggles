package com.example.mobile_client_app.features.membership.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.features.membership.main.domain.model.*
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
import kotlinx.datetime.LocalDate
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
    data class CheckoutInit(val data: CheckoutInitResponse) : MembershipEvent()
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

    var agreements = mutableStateListOf<AgreementDto>()
        private set

    var dialogAgreement by mutableStateOf<AgreementDto?>(null)

    var showDatePicker by mutableStateOf(false)
        private set

    var showAgreementDialog by mutableStateOf(false)
        private set

    var startDate by mutableStateOf<LocalDate?>(null)
        private set

    @OptIn(ExperimentalTime::class)
    var today = Clock.System.now().toEpochMilliseconds()
        private set

    @OptIn(ExperimentalTime::class)
    var maxDate = Clock.System.now().plus(duration = 7.days).toEpochMilliseconds()
        private set

    @OptIn(ExperimentalTime::class)
    var minDate = Clock.System.now().minus(duration = 1.days).toEpochMilliseconds()
        private set


    init {
        fetchMembershipPlans()
    }

    fun fetchMembershipPlans() {
        _uiState.value = UiState.Loading
        isConnected = checkInternetConnection()
        if (isConnected) {
            CoroutineScope(Dispatchers.Default).launch {
                getMembershipUseCase.invoke().onSuccess { response ->
                    plans = response
                    _uiState.value = UiState.Success(response)
                }.onError { error ->
                    _uiState.value = UiState.Error(error.displayMessage)
                }
            }
        } else {
            _uiState.value = UiState.Error("Failed to load data. Please check your connection.")
        }
    }

    fun updateShowDatePicker(isShowDatePicker: Boolean) {
        showDatePicker = isShowDatePicker
    }

    fun updateDateOfBirth(newDate: Long) {
        startDate = millisToDate(millis = newDate)
    }

    fun getSelectDateAsString(): String? {
        return startDate?.toString()
    }

    fun updatePromoCode(newPromoCode: String) {
        if (newPromoCode.length <= 8) promoCode = newPromoCode
    }

    fun updateSelectedPlan(newPlan: MembershipPlan) {
        selectedPlan = newPlan
        agreements.clear()
        agreements.addAll(newPlan.agreements?.map { it.toDto() } ?: emptyList())
    }

    fun onCheckInfo() {
        agreements.forEach {
            Log.debug { "Agreement ${it.title} : ${it.required} : ${it.selected}" }
        }
        when {
            selectedPlan == null -> handleMissingPlan()
            startDate == null -> handleMissingStartDate()
            promoCode.isNotBlank() -> validateAndApplyPromoCode()
            !allAgreementIsAccepted() -> showSnackbar("Please accept all agreements.")
            else -> sendCompleteRequest()
        }
    }

    private fun handleMissingPlan() {
        Log.debug { "Selected plan is null" }
        _events.value = MembershipEvent.ShowSnackbar("Select your plan")
    }

    private fun handleMissingStartDate() {
        _events.value = MembershipEvent.ShowSnackbar("You need to select the day that you want to start on it")
    }

    private fun validateAndApplyPromoCode() {
        when {
            promoCode.length != 8 -> {
                _events.value = MembershipEvent.ShowSnackbar("Promo code must be 8 digits")
            }

            selectedPlan == null -> {
                handleMissingPlan()
            }

            else -> {
                viewModelScope.launch {
                    Log.debug { "Promo code is $promoCode" }
                    Log.debug { "Selected plan is $selectedPlan" }
                    checkPromoCodeUseCase.invoke(promoCode, selectedPlan!!.id).onSuccess { response ->
                        Log.debug { "On Successful response: $response" }
                        if (response.isValid) sendCompleteRequest()
                        else showSnackbar(response.message)
                    }.onError { error ->
                        Log.debug { "Error response: $error" }
                        showSnackbar(error.displayMessage)
                    }
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        _events.value = MembershipEvent.ShowSnackbar(message)
    }

    fun sendCompleteRequest() {
        viewModelScope.launch {
            checkoutInitUseCase.invoke(
                CheckoutInitRequest(
                    membershipId = selectedPlan!!.id,
                    startDate = startDate!!,
                    promoCode = promoCode,
                    acceptAgreementsIds = agreements.filter { it.selected }.mapTo(mutableSetOf()) { it.id },
                )
            ).onSuccess { response ->
                _events.value = MembershipEvent.CheckoutInit(response)
            }.onError { error ->
                showSnackbar(error.displayMessage)
            }
        }
    }

    fun resetEvent() {
        _events.value = MembershipEvent.Reset
    }

    fun hasAgreement(): Boolean {
        Log.debug { "Checking agreement" }
        Log.debug { "Selected plan is $selectedPlan" }
        selectedPlan?.let { Log.debug { "Plan has agreement : ${it.agreements}" } }
        return selectedPlan != null && selectedPlan!!.agreements != null && agreements.isNotEmpty()
    }

    fun updateAgreementSelect(agreementId: Long) {
        val index = agreements.indexOfFirst { it.id == agreementId }
        if (index == -1) {
            Log.warn("Agreement $agreementId not found in list")
            return
        }

        agreements[index] = agreements[index].copy(
            selected = !agreements[index].selected
        )
    }

    fun showTermsDialog(agreement: AgreementDto?, show: Boolean) {
        dialogAgreement = agreement
        showAgreementDialog = show
    }


    /*
    * required && selected  → OK
    * required && !selected → FAIL
    * !required             → OK (regardless of selected)
    */
    fun allAgreementIsAccepted(): Boolean =
        agreements.all { !it.required || it.selected }
}