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
import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.membership.main.domain.usercase.GetMembershipUserCase
import com.example.mobile_client_app.util.network.checkInternetConnection
import com.example.mobile_client_app.util.network.networkError
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.random.Random

enum class Plan(val title: String, val price: String, val features: List<String>) {
    BASIC(
        "Basic",
        "$29 /month",
        listOf(
            "✓ Access to basic gym facilities",
            "✓ Group fitness classes",
            "✓ Personalized workout plans"
        )
    ),
    PRO(
        "Pro",
        "$49 /month",
        listOf(
            "✓ All Basic features",
            "✓ Advanced training equipment",
            "✓ Nutrition guidance"
        )
    ),
    PREMIUM(
        "Premium",
        "$79 /month",
        listOf(
            "✓ All Pro features",
            "✓ Exclusive gym areas",
            "✓ Priority booking for classes"
        )
    )
}

enum class ContractType {
    YEARLY, MONTHLY
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Error(val message: String) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}

// ViewModel remains the same
class MembershipViewModel(
    private val getMembershipUserCase: GetMembershipUserCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState<Plan>>(UiState.Loading)
    val uiState: StateFlow<UiState<Plan>> = _uiState

    var selectedPlan by mutableStateOf(Plan.BASIC)
    var contractType by mutableStateOf(ContractType.YEARLY)
    var startDate by mutableStateOf<LocalDate?>(null)
    var promoCode by mutableStateOf("")
        private set
    var plans by mutableStateOf<MembershipResponse?>(null)
    private set

    //need this code on start of app to know if token is available or not
    private var _token = MutableStateFlow("")
    val token = _token.asStateFlow()
    var isConnected = false

    init {
        fetchMembershipPlans()
    }

    /*init {
        viewModelScope.launch {
            dataStore.data.collect { storedData ->
                _token.update {
                    storedData[TOKEN_KEY].orEmpty()
                }
                print(_token.value)
            }
        }
    }*/

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
                    }.onSuccess {it->
                        plans = it
                    }
                }
            }
        }else{
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
}