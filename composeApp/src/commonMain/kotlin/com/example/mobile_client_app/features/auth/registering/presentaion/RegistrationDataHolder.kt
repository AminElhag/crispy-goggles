package com.example.mobile_client_app.features.auth.registering.presentaion

import com.example.mobile_client_app.features.auth.registering.presentaion.registering.PersonalInfoData

object RegistrationDataHolder {
    private var _personalInfoData: PersonalInfoData? = null

    fun setPersonalInfoData(data: PersonalInfoData) {
        _personalInfoData = data
    }

    fun getPersonalInfoData(): PersonalInfoData? = _personalInfoData

    fun clearData() {
        _personalInfoData = null
    }
}