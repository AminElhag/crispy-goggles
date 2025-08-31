package com.example.mobile_client_app.features.membership.payment.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.payment.domain.usecase.RequestPaymentUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

sealed class PaymentEvent {
    object Reset : PaymentEvent()
    data class ShowSnackbar(val message: String) : PaymentEvent()
    data class CheckoutInit(val data: CheckoutInitResponse) : PaymentEvent()
    data class PaymentComplete(val boolean: Boolean) : PaymentEvent()
}

private const val MAX_UNFORMATTED_LENGTH = 16
private const val MAX_FORMATTED_LENGTH = 19
const val MIN_CVV_LENGTH = 3
const val MAX_CVV_LENGTH = 4
private const val CARD_GROUP_SIZE = 4

class PaymentViewModel(
    val requestPaymentUseCase: RequestPaymentUseCase
) : ViewModel() {

    private val _events = MutableStateFlow<PaymentEvent>(PaymentEvent.Reset)
    val events: StateFlow<PaymentEvent> = _events

    var cardNumber by mutableStateOf(TextFieldValue(""))
        private set

    var expirationDate by mutableStateOf(TextFieldValue(""))
        private set

    var cardCVV by mutableStateOf(TextFieldValue(""))
        private set

    var isLoading by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set

    var errorDialogMessage by mutableStateOf<String?>(null)
    private set


    fun updateCardNumber(input: TextFieldValue) {

        val cleaned = input.text.filter { it.isDigit() }

        if (cleaned.length > MAX_UNFORMATTED_LENGTH) return

        val formatted = buildString {
            cleaned.forEachIndexed { index, char ->
                if (index > 0 && index % 4 == 0) {
                    append(" ")
                }
                append(char)
            }
        }

        val newCursorPosition = formatted.length

        cardNumber = TextFieldValue(
            text = formatted,
            selection = TextRange(newCursorPosition)
        )
    }

    fun updateExpirationDate(input: TextFieldValue) {
        var cleaned = input.text.filter { it.isDigit() }

        if (cleaned.length > 4) cleaned = cleaned.take(4)

        val formatted = when {
            cleaned.isEmpty() -> ""
            cleaned.length <= 2 -> cleaned
            else -> "${cleaned.take(2)}/${cleaned.drop(2)}"
        }

        val newCursorPosition = formatted.length

        expirationDate = TextFieldValue(
            text = formatted,
            selection = TextRange(newCursorPosition)
        )
    }

    fun updateCardCVV(input: TextFieldValue) {
        val filtered = input.text.filter { it.isDigit() }
        val truncated = if (filtered.length > MAX_CVV_LENGTH) {
            filtered.substring(0, MAX_CVV_LENGTH)
        } else {
            filtered
        }

        val newCursorPosition = truncated.length

        cardCVV = TextFieldValue(
            text = truncated,
            selection = TextRange(newCursorPosition)
        )
    }

    fun onCompleteClick(contractId: Long) {
        if (!isValidCardNumber(cardNumber.text.filter { it.isDigit() })) {
            _events.value = PaymentEvent.ShowSnackbar("Card number is invalid.")
        } else if (!isValidExpirationDate(expirationDate.text)) {
            _events.value = PaymentEvent.ShowSnackbar("Card is expired.")
        } else if (!isValidCVV(cardCVV.text)) {
            _events.value = PaymentEvent.ShowSnackbar("Cvv is invalid.")
        } else {
            isLoading = true
            viewModelScope.launch {
                requestPaymentUseCase.invoke(
                    contractId, cardNumber.text, expirationDate.text, cardCVV.text
                ).onSuccess { response ->
                    isLoading = false
                    if (response.success) {
                        _events.value = PaymentEvent.PaymentComplete(true)
                    } else {
                        errorDialogMessage = response.message
                        showErrorDialog = true
                    }
                }.onError { error ->
                    isLoading = false
                    _events.value = PaymentEvent.ShowSnackbar(error.displayMessage)
                }
            }
        }
    }

    private fun isValidCardNumber(cardNumber: String): Boolean {
        if (cardNumber.length < 13 || cardNumber.length > 19) return false

        return luhnCheck(cardNumber)
    }

    private fun luhnCheck(cardNumber: String): Boolean {
        var sum = 0
        var alternate = false

        for (i in cardNumber.length - 1 downTo 0) {
            var digit = cardNumber[i].digitToIntOrNull() ?: return false

            if (alternate) {
                digit *= 2
                if (digit > 9) {
                    digit = (digit % 10) + 1
                }
            }
            sum += digit
            alternate = !alternate
        }

        return sum % 10 == 0
    }

    @OptIn(ExperimentalTime::class)
    private fun isValidExpirationDate(expiration: String): Boolean {
        if (!expiration.matches(Regex("\\d{2}/\\d{2}"))) return false

        val parts = expiration.split("/")
        val month = parts[0].toIntOrNull() ?: return false
        val year = parts[1].toIntOrNull() ?: return false

        if (month < 1 || month > 12) return false

        val fullYear = 2000 + year

        val date = kotlinx.datetime.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        val currentYear = date.year
        val currentMonth = date.month.number

        return if (fullYear > currentYear) {
            true
        } else if (fullYear == currentYear) {
            month >= currentMonth
        } else {
            false
        }
    }

    private fun isValidCVV(cvv: String): Boolean {
        return cvv.length in 3..4 && cvv.all { it.isDigit() }
    }

    fun resetEvent() {
        _events.value = PaymentEvent.Reset
    }

    fun updateShowErrorDialog(boolean: Boolean){
        showErrorDialog = boolean
    }
}