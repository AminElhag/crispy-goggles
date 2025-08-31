package com.example.mobile_client_app.util.network

import dev.tmapps.konnection.Konnection

fun checkInternetConnection(

): Boolean {
    val konnection = Konnection.createInstance()
    val hasNetworkConnection = konnection.isConnected()
    return hasNetworkConnection
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
    return isConnected*//*
    return true*/
}