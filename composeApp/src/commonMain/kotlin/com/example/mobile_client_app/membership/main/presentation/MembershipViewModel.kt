package com.example.mobile_client_app.membership.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.component.toDDMMYYY
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

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

// ViewModel remains the same
class MembershipViewModel : ViewModel() {
    var selectedPlan by mutableStateOf(Plan.BASIC)
    var contractType by mutableStateOf(ContractType.YEARLY)
    var startDate by mutableStateOf<LocalDate?>(null)
    var promoCode by mutableStateOf("")
        private set

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