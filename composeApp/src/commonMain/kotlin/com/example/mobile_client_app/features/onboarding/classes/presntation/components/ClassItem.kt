package com.example.mobile_client_app.features.onboarding.classes.presntation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass

@Composable
fun ClassItem(
    fitnessClass: FitnessClass,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fitnessClass.time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = fitnessClass.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Trainer: ${fitnessClass.trainer}",
                    fontSize = 14.sp,
                )
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (fitnessClass.name) {
                            "Morning Yoga Flow" -> Color(0xFFE8F5E8)
                            "HIIT Blast" -> Color(0xFFE3F2FD)
                            "Pilates Core" -> Color(0xFFF3E5F5)
                            else -> Color(0xFFFFF3E0)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (fitnessClass.name) {
                        "Morning Yoga Flow" -> Icons.Default.Person
                        "HIIT Blast" -> Icons.Default.Schedule
                        "Pilates Core" -> Icons.Default.Person
                        else -> Icons.Default.Schedule
                    },
                    contentDescription = fitnessClass.name,
                    tint = when (fitnessClass.name) {
                        "Morning Yoga Flow" -> Color(0xFF4CAF50)
                        "HIIT Blast" -> Color(0xFF2196F3)
                        "Pilates Core" -> Color(0xFF9C27B0)
                        else -> Color(0xFFFF9800)
                    }
                )
            }
        }
    }
}