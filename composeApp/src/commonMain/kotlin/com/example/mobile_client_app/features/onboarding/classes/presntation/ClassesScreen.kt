package com.example.mobile_client_app.features.onboarding.classes.presntation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.features.onboarding.classes.domain.model.ClassFilter
import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass
import com.example.mobile_client_app.features.onboarding.classes.presntation.components.ClassItem
import com.example.mobile_client_app.features.onboarding.classes.presntation.components.DateSectionHeader
import com.example.mobile_client_app.features.onboarding.classes.presntation.components.DateSeparator
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.classes
import mobile_client_app.composeapp.generated.resources.today
import mobile_client_app.composeapp.generated.resources.tomorrow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassesScreen(
    viewModel: ClassesViewModel = koinViewModel(),
    onClassClick: (id: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        FullScreenLoading()
    } else if (uiState.error != null) {
        FullScreenError(uiState.error!!, onRetry = {
            viewModel.refresh()
        })
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(Res.string.classes)) },
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    items(ClassFilter.entries) { filter ->
                        FilterChip(
                            selected = uiState.selectedFilter == filter,
                            onClick = { viewModel.selectFilter(filter) },
                            label = {
                                Text(
                                    text = stringResource(filter.displayName), fontSize = 14.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.secondary,
                            )
                        )
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    when (uiState.selectedFilter) {
                        ClassFilter.ALL -> {
                            val todayClasses =
                                viewModel.filteredClasses.value.filter { it.date == FitnessClass.ClassDate.TODAY }
                            val tomorrowClasses =
                                viewModel.filteredClasses.value.filter { it.date == FitnessClass.ClassDate.TOMORROW }

                            if (todayClasses.isNotEmpty()) {
                                item {
                                    DateSectionHeader(title = stringResource(Res.string.today))
                                }
                                items(todayClasses) { fitnessClass ->
                                    ClassItem(
                                        fitnessClass = fitnessClass, onClick = { onClassClick(fitnessClass.id) })
                                }
                            }

                            if (todayClasses.isNotEmpty() && tomorrowClasses.isNotEmpty()) {
                                item {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    DateSeparator()
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            if (tomorrowClasses.isNotEmpty()) {
                                item {
                                    DateSectionHeader(title = stringResource(Res.string.tomorrow))
                                }
                                items(tomorrowClasses) { fitnessClass ->
                                    ClassItem(
                                        fitnessClass = fitnessClass, onClick = { onClassClick(fitnessClass.id) })
                                }
                            }
                        }

                        else -> {
                            items(viewModel.filteredClasses.value) { fitnessClass ->
                                ClassItem(
                                    fitnessClass = fitnessClass, onClick = { onClassClick(fitnessClass.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}