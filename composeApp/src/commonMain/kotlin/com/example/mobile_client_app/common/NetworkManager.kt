package com.example.mobile_client_app.common

import com.mirego.konnectivity.Konnectivity
import com.mirego.konnectivity.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import saschpe.log4k.Log
import kotlin.coroutines.CoroutineContext

object NetworkManager {
    private val konnectivity = Konnectivity()

    val networkState: Flow<NetworkState>
        get() = konnectivity.networkState
}

suspend fun checkInternetConnection(context: CoroutineContext): Boolean {
    var isConnected = false
    Log.debug("Debugging: Check internet connection")
    CoroutineScope(context).launch {
        NetworkManager.networkState.collectLatest { networkState ->
            isConnected = when (networkState) {
                is NetworkState.Reachable -> true
                NetworkState.Unreachable -> false
            }
        }
    }
    Log.debug("Debugging: Internet connection : $isConnected")
    return isConnected
}