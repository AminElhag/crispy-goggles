package com.example.mobile_client_app.util.network

import kotlinx.coroutines.CoroutineScope

fun checkInternetConnection(
    scope: CoroutineScope,
): Boolean {
    /*var isConnected = false
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
    return isConnected*/
    return true
}