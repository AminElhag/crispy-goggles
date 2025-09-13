package com.example.mobile_client_app.features.auth.registering.presentaion

import com.example.mobile_client_app.common.HearAboutUs
import com.example.mobile_client_app.common.MedicalCondition
import com.example.mobile_client_app.common.countryPicker.Country
import com.example.mobile_client_app.features.auth.registering.presentaion.registering.PersonalInfoData
import kotlinx.datetime.LocalDate

data class PersonalInfoState(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val idNumber: String = "",
    val dataOfBirth: LocalDate? = null,
    val showDatePicker: Boolean = false,
    val isMale: Boolean = true,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordStrength: Float = 0f,
    val selectedCountry: Country? = null,
    val isCountrySelectorExpanded: Boolean = false,
    val phoneNumber: String = "",
    val email: String = ""
)

data class AdditionalInfoState(
    val emergencyContact: String = "",
    val occupation: String = "",
    val hearAboutUs: HearAboutUs? = null,
    val medicalConditions: List<MedicalCondition> = emptyList(),
    val isHearAboutUsSelectorExpanded: Boolean = false,
    val isMedicalConditionExpanded: Boolean = false
)

object RegistrationDataHolder {
    private var _personalInfoState: PersonalInfoState? = null
    private var _additionalInfoState: AdditionalInfoState? = null

    fun setPersonalInfoState(state: PersonalInfoState) {
        _personalInfoState = state
    }

    fun getPersonalInfoState(): PersonalInfoState? = _personalInfoState

    fun setAdditionalInfoState(state: AdditionalInfoState) {
        _additionalInfoState = state
    }

    fun getAdditionalInfoState(): AdditionalInfoState? = _additionalInfoState

    fun clearData() {
        _personalInfoState = null
        _additionalInfoState = null
    }
}