package com.example.mobile_client_app.common.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.models.DateRangePickerConfig
import kotlinx.datetime.LocalDate

@Composable
fun DateRangePicker(
    currentMonth: LocalDate,
    onPreviousMonthChange: () -> Unit,
    onNextMonthChange: () -> Unit,
    startDate: LocalDate?,
    onStartDateSelected: (LocalDate?) -> Unit,
    endDate: LocalDate? = null,
    onEndDateSelected: (LocalDate?) -> Unit,
    config: DateRangePickerConfig,
    onErrorMessage: (String?) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPreviousMonthChange
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
                onClick = onNextMonthChange
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
                onErrorMessage(null)

                config.validateDateSelection(selectedDate)?.let { error ->
                    onErrorMessage(error)
                    return@CalendarGrid
                }

                if (config.allowStartDateSelection) {
                    onStartDateSelected(selectedDate)
                    onEndDateSelected(null)
                }
            }
        )
    }
}