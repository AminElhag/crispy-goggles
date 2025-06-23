package com.example.mobile_client_app.common

import com.mirego.konnectivity.Konnectivity
import com.mirego.konnectivity.NetworkState
import kotlinx.coroutines.flow.Flow

object NetworkManager {
    private val konnectivity = Konnectivity()

    val networkState: Flow<NetworkState>
        get() = konnectivity.networkState
}