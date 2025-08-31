package com.example.mobile_client_app.common.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.models.DateRangePickerConfig
import kotlinx.datetime.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun CalendarGrid(
    currentMonth: LocalDate,
    startDate: LocalDate?,
    endDate: LocalDate?,
    config: DateRangePickerConfig,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val daysInMonth = currentMonth.daysUntil(currentMonth.plus(1, DateTimeUnit.MONTH))
    val firstDayOfMonth = LocalDate(currentMonth.year, currentMonth.month, 1)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    val dayNames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Column(
        modifier = modifier,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.weight(1f)
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.weight(7f)
        ) {
            items(startDayOfWeek) {
                Spacer(modifier = Modifier.height(40.dp))
            }

            items(daysInMonth) { dayIndex ->
                val date = firstDayOfMonth.plus(dayIndex, DateTimeUnit.DAY)
                val isSelected = date == startDate || date == endDate
                val isInRange = startDate != null && endDate != null &&
                        date > startDate && date < endDate
                val isToday = date == today

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