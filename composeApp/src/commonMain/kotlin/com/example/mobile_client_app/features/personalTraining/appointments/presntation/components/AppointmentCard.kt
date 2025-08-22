package com.example.mobile_client_app.features.personalTraining.appointments.presntation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment

@Composable
fun AppointmentCard(
    appointment: Appointment,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = appointment.date,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (appointment.status != Appointment.AppointmentStatus.SCHEDULED) {
                        Spacer(modifier = Modifier.height(8.dp))
                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    appointment.status.name.lowercase().replaceFirstChar { it.uppercase() },
                                    fontSize = 12.sp
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color.Transparent,
                                labelColor = when (appointment.status) {
                                    Appointment.AppointmentStatus.IN_PROGRESS -> Color(0xFFFF9800)
                                    Appointment.AppointmentStatus.COMPLETED -> Color(0xFF4CAF50)
                                    Appointment.AppointmentStatus.CANCELLED -> Color(0xFFF44336)
                                    else -> Color(0xFF757575)
                                }
                            )
                        )
                    }
                }
                Text(
                    text = appointment.timeRange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Trainer: ${appointment.trainer.name}",
                    fontSize = 14.sp
                )

                if (appointment.status == Appointment.AppointmentStatus.SCHEDULED) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFF44336)
                        ),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Cancel", fontSize = 12.sp)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        when (appointment.trainer.name) {
                            "Alex" -> Color(0xFF2E7D32)
                            "Chris" -> Color(0xFF1976D2)
                            else -> Color(0xFF757575)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = appointment.trainer.name.first().toString(),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}