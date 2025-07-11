package com.example.mobile_client_app.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import okio.Path.Companion.toPath
import org.koin.core.module.Module

@OptIn(InternalCoroutinesApi::class)
private val lock = SynchronizedObject() // Used for thread safety
private lateinit var dataStore: DataStore<Preferences> // Late-initialized variable

@OptIn(InternalCoroutinesApi::class)
fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return synchronized(lock) {
        if (::dataStore.isInitialized) {
            dataStore
        } else {
            PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
                .also { dataStore = it }
        }
    }
}

internal const val DATA_STORE_FILE_NAME = "storage.preferences_pb"