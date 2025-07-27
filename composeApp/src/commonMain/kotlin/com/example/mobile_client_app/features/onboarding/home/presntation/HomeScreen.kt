package com.example.mobile_client_app.features.onboarding.home.presntation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.common.component.InfiniteAutoScrollLazyRow
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.BannerResponse
import com.example.mobile_client_app.features.onboarding.home.presntation.components.BannerCard
import com.example.mobile_client_app.features.onboarding.home.presntation.components.ClassCard
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onNotificationsClick: () -> Unit,
    onBannerClick: (deepLink: String) -> Unit,
    onViewClassClick: (classId: Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        FullScreenLoading()
    } else if (uiState.error != null) {
        FullScreenError(uiState.error!!, onRetry = {
            viewModel.retry()
        })
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    actions = {
                        IconButton(onClick = { onNotificationsClick() }) {
                            Icon(
                                imageVector = viewModel.notificationIcon(),
                                contentDescription = "Notifications"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            ) {
                InfiniteAutoScrollLazyRow(
                    viewModel.banners.value,
                    itemCard = { BannerCard(
                        it,
                        onClick = {
                            it.deepLink?.let {deeplink->
                                onBannerClick(deeplink)
                            }
                        }
                    ) },
                )
                LazyColumn {
                    items(
                        viewModel.upcomingClasses.value
                    ) { it ->
                        ClassCard(it, onClassClick = {
                            onViewClassClick(it.id)
                        })
                    }
                }
            }
        }
    }

}
