package com.example.mobile_client_app.common.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSection(
    datePickerState: DatePickerState,
    hasOutTitle: Boolean = true,
    title: String = "",
    onClick: () -> Unit,
    selectDate: String? = null,
    hintString: String,
    showDatePicker: Boolean,
    updateShowDatePicker: (Boolean) -> Unit,
    updateDateOfBirth: (Long) -> Unit,
    okString: String,
    cancelString: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        if (hasOutTitle) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
        Card(
            shape = RoundedCornerShape(25),
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (selectDate != null) {
                    Text(
                        selectDate,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        text = hintString,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Expand"
                    )
                }
            }
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { updateShowDatePicker(false) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                updateDateOfBirth(datePickerState.selectedDateMillis ?: 0)
                                updateShowDatePicker(false)
                            }
                        ) {
                            Text(okString)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { updateShowDatePicker(false) }
                        ) {
                            Text(cancelString)
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}