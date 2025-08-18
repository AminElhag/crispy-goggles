package com.example.mobile_client_app.features.auth.profile.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.features.auth.profile.data.remote.models.HealthMetricResponse
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.body_health_status
import org.jetbrains.compose.resources.stringResource

@Composable
fun HealthStatusSection(
    healthMetrics: List<HealthMetricResponse>,
    getChangeColor: (Boolean?) -> Color?
) {
    Text(
        text = stringResource(Res.string.body_health_status),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    LazyColumn {
        itemsIndexed(healthMetrics) { index, metric ->
            HealthMetricItemWithIcon(
                healthMetric = metric,
                changeColor = getChangeColor(metric.isPositiveChange)
            )
            if (index < healthMetrics.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}