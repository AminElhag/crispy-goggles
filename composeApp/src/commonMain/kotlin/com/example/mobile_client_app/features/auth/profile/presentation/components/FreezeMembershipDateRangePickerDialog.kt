package com.example.mobile_client_app.features.auth.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobile_client_app.features.auth.profile.domain.models.DateRange
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class DateRangePickerConfig(
    val allowPastDates: Boolean = true,
    val minDate: LocalDate? = null,
    val maxDate: LocalDate? = null,
    val maxRangeDays: Int? = null, // Maximum number of days in the range
    val minRangeDays: Int? = null, // Minimum number of days in the range
    val allowSingleDate: Boolean = true, // Whether start and end can be the same
    val allowStartDateSelection: Boolean = true,
    val allowEndDateSelection: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun FreezeMembershipDateRangePickerDialog(
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

    // Validation function
    fun validateDateSelection(selectedDate: LocalDate): String? {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        // Check if past dates are allowed
        if (!config.allowPastDates && selectedDate < today) {
            return "Past dates are not allowed"
        }

        // Check min/max date constraints
        config.minDate?.let { min ->
            if (selectedDate < min) {
                return "Date cannot be before ${formatDate(min)}"
            }
        }

        config.maxDate?.let { max ->
            if (selectedDate > max) {
                return "Date cannot be after ${formatDate(max)}"
            }
        }

        return null
    }

    fun validateDateRange(start: LocalDate, end: LocalDate): String? {
        if (!config.allowSingleDate && start == end) {
            return "Start and end dates cannot be the same"
        }

        val rangeDays = start.daysUntil(end).toInt() + 1

        config.minRangeDays?.let { min ->
            if (rangeDays < min) {
                return "Date range must be at least $min days"
            }
        }

        config.maxRangeDays?.let { max ->
            if (rangeDays > max) {
                return "Date range cannot exceed $max days"
            }
        }

        return null
    }

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

                // Configuration info (optional display)
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

                // Selected dates display
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

                // Error message
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

                // Month navigation
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

                // Calendar
                CalendarGrid(
                    currentMonth = currentMonth,
                    startDate = startDate,
                    endDate = endDate,
                    config = config,
                    onDateSelected = { selectedDate ->
                        errorMessage = null

                        // Validate the selected date first
                        validateDateSelection(selectedDate)?.let { error ->
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
                                validateDateRange(startDate!!, tempEndDate)?.let { error ->
                                    errorMessage = error
                                    return@CalendarGrid
                                }
                                endDate = tempEndDate
                            }

                            endDate == null && config.allowEndDateSelection && selectedDate < startDate!! -> {
                                val tempStartDate = selectedDate
                                val tempEndDate = startDate!!
                                validateDateRange(tempStartDate, tempEndDate)?.let { error ->
                                    errorMessage = error
                                    return@CalendarGrid
                                }
                                endDate = tempEndDate
                                startDate = tempStartDate
                            }

                            config.allowStartDateSelection -> {
                                // Reset and start new selection
                                startDate = selectedDate
                                endDate = null
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
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
                        // If both start and end date selection are disabled, nothing can be selected
                        !config.allowStartDateSelection && !config.allowEndDateSelection -> false

                        // If only start date selection is allowed
                        config.allowStartDateSelection && !config.allowEndDateSelection -> startDate != null

                        // If only end date selection is allowed
                        !config.allowStartDateSelection && config.allowEndDateSelection -> endDate != null

                        // If both are allowed, check if we need both dates or just one
                        config.allowStartDateSelection && config.allowEndDateSelection -> {
                            when {
                                // If single date is allowed and we have a start date, that's complete
                                config.allowSingleDate && startDate != null && endDate == null -> true

                                // If single date is not allowed, we need both start and end dates
                                !config.allowSingleDate -> startDate != null && endDate != null

                                // If single date is allowed, we're complete with start date or both dates
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

@OptIn(ExperimentalTime::class)
@Composable
private fun CalendarGrid(
    currentMonth: LocalDate,
    startDate: LocalDate?,
    endDate: LocalDate?,
    config: DateRangePickerConfig,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val daysInMonth = currentMonth.daysUntil(currentMonth.plus(1, DateTimeUnit.MONTH))
    val firstDayOfMonth = LocalDate(currentMonth.year, currentMonth.month, 1)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    // Day headers
    val dayNames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Column {
        // Day headers
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(dayNames) { day ->
                Text(
                    text = day,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Calendar days
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Empty cells for days before the first day of the month
            items(startDayOfWeek) {
                Spacer(modifier = Modifier.height(40.dp))
            }

            // Days of the month
            items(daysInMonth) { dayIndex ->
                val date = firstDayOfMonth.plus(dayIndex, DateTimeUnit.DAY)
                val isSelected = date == startDate || date == endDate
                val isInRange = startDate != null && endDate != null &&
                        date > startDate && date < endDate
                val isToday = date == today

                // Check if date is selectable based on config
                val isSelectable = when {
                    !config.allowPastDates && date < today -> false
                    config.minDate != null && date < config.minDate -> false
                    config.maxDate != null && date > config.maxDate -> false
                    else -> true
                }

                CalendarDay(
                    date = date,
                    isSelected = isSelected,
                    isInRange = isInRange,
                    isToday = isToday,
                    isSelectable = isSelectable,
                    onClick = {
                        if (isSelectable) {
                            onDateSelected(date)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    isInRange: Boolean,
    isToday: Boolean,
    isSelectable: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isInRange -> MaterialTheme.colorScheme.primaryContainer
        isToday -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        else -> Color.Transparent
    }

    val textColor = when {
        !isSelectable -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isInRange -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .padding(2.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(
                if (isSelectable) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}

private fun formatDateRange(startDate: LocalDate?, endDate: LocalDate?, config: DateRangePickerConfig): String {
    return when {
        startDate == null -> if (config.allowStartDateSelection) "No dates selected" else "Start date disabled"
        endDate == null -> if (config.allowEndDateSelection) "Start: ${formatDate(startDate)}" else "End: ${formatDate(startDate)}"
        else -> "${formatDate(startDate)} - ${formatDate(endDate)}"
    }
}

private fun formatDate(date: LocalDate): String {
    return "${date.dayOfMonth} ${date.month.name.take(3)} ${date.year}"
}