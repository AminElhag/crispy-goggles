package com.example.mobile_client_app.util.network

import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.NetworkManager
import com.mirego.konnectivity.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import saschpe.log4k.Log

fun checkInternetConnection(
    scope: CoroutineScope,
):Boolean {
    var isConnected = false
    scope.launch {
        NetworkManager.networkState.collectLatest { networkState ->
            Log.debug { "Internet is connection : ${networkState}" }
            isConnected = when (networkState) {
                is NetworkState.Reachable -> true
                NetworkState.Unreachable -> false
            }
        }
    }
    Log.debug { "Internet is connection : $isConnected" }
    return isConnected
}