package com.example.mobile_client_app.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TokenManager(private val dataStore: DataStore<Preferences>) {
    private var currentToken: String? by mutableStateOf(null)
    private var initializationComplete: Boolean by mutableStateOf(false)

    init {
        // Load token once during initialization
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.data.collect { preferences ->
                currentToken = preferences[TOKEN_KEY] ?: ""
                initializationComplete = true
            }
        }
    }

    fun getToken(): String? {
        if (!initializationComplete) {
            // Optional: Add warning log about token not initialized
        }
        return currentToken
    }
}