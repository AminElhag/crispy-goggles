package com.example.mobile_client_app.membership.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.TOKEN_KEY
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.component.toDDMMYYY
import com.example.mobile_client_app.membership.main.domain.model.ContractOption
import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.membership.main.domain.model.PlanResponse
import com.example.mobile_client_app.membership.main.domain.usercase.GetMembershipUserCase
import com.example.mobile_client_app.util.network.checkInternetConnection
import com.example.mobile_client_app.util.network.networkError
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime


enum class ContractType {
    YEARLY, MONTHLY
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Error(val message: String) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}

class MembershipViewModel(
    private val getMembershipUserCase: GetMembershipUserCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState<MembershipResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<MembershipResponse>> = _uiState

    private var _token = MutableStateFlow("")
    val token = _token.asStateFlow()
    var isConnected = false

    var contractType by mutableStateOf(ContractType.YEARLY)
    var startDate by mutableStateOf<LocalDate?>(null)
    var promoCode by mutableStateOf("")
        private set
    var plans by mutableStateOf<MembershipResponse?>(null)
        private set
    var selectedPlan by mutableStateOf<PlanResponse?>(null)
        private set
    var contractOptions: List<ContractOption> = emptyList()
        private set
    var selectedContractOption by mutableStateOf<ContractOption?>(null)
        private set

    init {
        fetchMembershipPlans()
    }

    fun fetchMembershipPlans() {
        _uiState.value = UiState.Loading
        isConnected = checkInternetConnection(viewModelScope)
        if (isConnected) {
            CoroutineScope(Dispatchers.Default).launch {
                dataStore.data.collect { storedData ->
                    _token.update {
                        storedData[TOKEN_KEY].orEmpty()
                    }
                    getMembershipUserCase.invoke(_token.value).onError {
                        _uiState.value = UiState.Error(networkError(it))
                    }.onSuccess { it ->
                        plans = it
                        if (plans != null) {
                            _uiState.value = UiState.Success(it)
                        } else {
                            _uiState.value = UiState.Error("Server error")
                        }
                    }
                }
            }
        } else {
            _uiState.value = UiState.Error("Failed to load data. Please check your connection.")
        }


        /*viewModelScope.launch {
            getMembershipUserCase.invoke(token.value);
            *//*try {
                apiState.value = ApiState.Loading
                val response = MembershipApiService.getPlans()
                plans.value = response
                apiState.value = ApiState.Success
            } catch (e: Exception) {
                apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }*//*
        }*/
    }

    var showDatePicker by mutableStateOf(false)
        private set

    var dataOfBirth by mutableStateOf<LocalDateTime?>(null)
        private set

    fun updateShowDatePicker(isShowDatePicker: Boolean) {
        showDatePicker = isShowDatePicker
    }

    fun updateDateOfBirth(newDate: Long) {
        dataOfBirth = millisToDate(millis = newDate)
    }

    fun getSelectDateAsString(): String? {
        return dataOfBirth.toDDMMYYY()
    }

    fun updatePromoCode(newPromoCode: String) {
        promoCode = newPromoCode
    }

    fun updateSelectedPlan(newPlan: PlanResponse) {
        selectedPlan = newPlan
        contractOptions = newPlan.contractOptions
        newPlan.contractOptions.forEach { contract ->
            if (contract.default) selectedContractOption = contract
        }
    }

    fun isContractOptionSelected(
        contractOption: ContractOption,
    ):Boolean {
        return selectedContractOption?.id == contractOption.id
    }

    fun selectContractOption(contractOption: ContractOption) {
        selectedContractOption = contractOption
    }
}