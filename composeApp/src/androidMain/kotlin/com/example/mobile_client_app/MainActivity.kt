package com.example.mobile_client_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.mobile_client_app.features.auth.login.di.loginModule
import com.example.mobile_client_app.features.auth.registering.di.registeringModule
import com.example.mobile_client_app.common.DATA_STORE_FILE_NAME
import com.example.mobile_client_app.common.createDataStore
import com.example.mobile_client_app.di.dataStoreModule
import com.example.mobile_client_app.di.networkModule
import com.example.mobile_client_app.features.membership.main.di.membershipModule
import com.example.mobile_client_app.features.membership.payment.di.paymentModule
import com.example.mobile_client_app.features.notifications.di.notificationsModule
import com.example.mobile_client_app.features.onboarding.di.onBoardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(
                listOf(
                    loginModule,
                    androidKoinModules,
                    networkModule,
                    registeringModule,
                    dataStoreModule,
                    membershipModule,
                    paymentModule,
                    onBoardingModule,
                    notificationsModule,
                )
            )
        }
        setContent {
            App()
        }
    }
}

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}