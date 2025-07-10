package com.example.mobile_client_app.features.membership.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.features.membership.main.domain.model.PlanResponse


@Composable
fun PlanCard(
    plan: PlanResponse,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.2f
            ),
        ),
        border = /*if (isSelected) CardDefaults.outlinedCardBorder() else null*/ CardDefaults.outlinedCardBorder(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Plan title
            Text(
                text = plan.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = /*if (isSelected) Color(0xFF2E7D32) else*/ MaterialTheme.colorScheme.onSurface
            )

            // Price
            Text(
                text = plan.price,
                style = MaterialTheme.typography.titleMedium,
            )

            // Choose button
            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = /*if (isSelected) Color(0xFF4CAF50) else */MaterialTheme.colorScheme.onPrimary,
                    contentColor = Color.White
                )
            ) {
                Text("Choose Plan")
            }

            // Features
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                plan.features.forEach { feature ->
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}