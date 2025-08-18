package com.example.mobile_client_app.features.auth.profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.features.auth.profile.data.remote.models.HealthMetricResponse
import com.example.mobile_client_app.features.auth.profile.domain.models.HeathMetricIcon
import com.example.mobile_client_app.features.auth.profile.domain.models.getIcon

@Composable
fun HealthMetricItemWithIcon(
    healthMetric: HealthMetricResponse,
    changeColor: Color?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            /* Text(
                 text = *//*healthMetric.icon*//*,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 16.dp)
            )*/
            Icon(
                imageVector = getIcon(HeathMetricIcon.fromId(healthMetric.iconId)),
                contentDescription = healthMetric.title,
                modifier = Modifier.padding(8.dp)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = healthMetric.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = healthMetric.subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = healthMetric.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            healthMetric.change?.let { change ->
                changeColor?.let { color ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = change,
                        color = color,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}