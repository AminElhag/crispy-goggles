package com.example.mobile_client_app.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.map

@Composable
fun rememberTokenState(dataStore: DataStore<Preferences>): TokenState {
    val token by dataStore.data
        .map { preferences -> preferences[TOKEN_KEY] }
        .collectAsState(initial = null)

    return TokenState(
        token = token,
        hasToken = !token.isNullOrEmpty(),
        isLoading = token == null
    )
}

data class TokenState(
    val token: String?,
    val hasToken: Boolean,
    val isLoading: Boolean
)