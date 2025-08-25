package com.example.mobile_client_app.features.personalTraining.trainerSelection.presntation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.theme.GoldColor

@Composable
fun TrainerItem(
    trainerResponse: TrainerResponse,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceContainerHighest) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = trainerResponse.name.split(" ").map { it.first() }.joinToString(""),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = trainerResponse.name,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = GoldColor
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${trainerResponse.rating}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "• ${trainerResponse.reviewCount} reviews",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            /*if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Select,
                    contentDescription = "Selected",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Cyan
                )
            }*/
        }
    }
}