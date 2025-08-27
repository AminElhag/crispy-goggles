package com.example.mobile_client_app.common.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobile_client_app.common.formatDate
import com.example.mobile_client_app.common.formatDateRange
import com.example.mobile_client_app.common.models.DateRangePickerConfig
import com.example.mobile_client_app.features.auth.profile.domain.models.DateRange
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onDateRangeSelected: (DateRange) -> Unit,
    initialStartDate: LocalDate? = null,
    initialEndDate: LocalDate? = null,
    config: DateRangePickerConfig = DateRangePickerConfig()
) {
    var currentMonth by remember { mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault())) }
    var startDate by remember { mutableStateOf(initialStartDate) }
    var endDate by remember { mutableStateOf(initialEndDate) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Date Range",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!config.allowPastDates || config.maxRangeDays != null || config.minRangeDays != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Constraints:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (!config.allowPastDates) {
                                Text(
                                    text = "• Past dates not allowed",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            config.maxRangeDays?.let { max ->
                                Text(
                                    text = "• Maximum range: $max days",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            config.minRangeDays?.let { min ->
                                Text(
                                    text = "• Minimum range: $min days",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (!config.allowSingleDate) {
                                Text(
                                    text = "• Start and end dates must be different",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Selected Range:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = formatDateRange(startDate, endDate, config),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                errorMessage?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = error,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            currentMonth = currentMonth.minus(1, DateTimeUnit.MONTH)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous month"
                        )
                    }

                    Text(
                        text = "${currentMonth.month.name} ${currentMonth.year}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = {
                            currentMonth = currentMonth.plus(1, DateTimeUnit.MONTH)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next month"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CalendarGrid(
                    currentMonth = currentMonth,
                    startDate = startDate,
                    endDate = endDate,
                    config = config,
                    onDateSelected = { selectedDate ->
                        errorMessage = null
                        config.validateDateSelection(selectedDate)?.let { error ->
                            errorMessage = error
                            return@CalendarGrid
                        }

                        when {
                            startDate == null && config.allowStartDateSelection -> {
                                startDate = selectedDate
                                endDate = null
                            }

                            endDate == null && config.allowEndDateSelection && selectedDate >= startDate!! -> {
                                val tempEndDate = selectedDate
                                config.validateDateRange(startDate!!, tempEndDate)?.let { error ->
                                    errorMessage = error
                                    return@CalendarGrid
                                }
                                endDate = tempEndDate
                            }

                            endDate == null && config.allowEndDateSelection && selectedDate < startDate!! -> {
                                val tempStartDate = selectedDate
                                val tempEndDate = startDate!!
                                config.validateDateRange(tempStartDate, tempEndDate)?.let { error ->
                                    errorMessage = error
                                    return@CalendarGrid
                                }
                                endDate = tempEndDate
                                startDate = tempStartDate
                            }

                            config.allowStartDateSelection -> {
                                startDate = selectedDate
                                endDate = null
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            startDate = null
                            endDate = null
                            errorMessage = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Clear")
                    }

                    val isSelectionComplete = when {
                        !config.allowStartDateSelection && !config.allowEndDateSelection -> false

                        config.allowStartDateSelection && !config.allowEndDateSelection -> startDate != null

                        !config.allowStartDateSelection && config.allowEndDateSelection -> endDate != null

                        config.allowStartDateSelection && config.allowEndDateSelection -> {
                            when {
                                config.allowSingleDate && startDate != null && endDate == null -> true

                                !config.allowSingleDate -> startDate != null && endDate != null

                                config.allowSingleDate -> startDate != null && (endDate != null || true)

                                else -> startDate != null && endDate != null
                            }
                        }

                        else -> false
                    }

                    Button(
                        onClick = {
                            onDateRangeSelected(DateRange(startDate, endDate))
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = isSelectionComplete && errorMessage == null
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}