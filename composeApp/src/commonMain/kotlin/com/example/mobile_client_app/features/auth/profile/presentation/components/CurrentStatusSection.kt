package com.example.mobile_client_app.features.auth.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.features.auth.profile.data.remote.models.StatusMetricResponse
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.current_status
import org.jetbrains.compose.resources.stringResource

@Composable
fun CurrentStatusSection(
    statusMetrics: List<StatusMetricResponse>,
    cardBackgroundColor: Color
) {
    Text(
        text = stringResource(Res.string.current_status),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        statusMetrics.forEach { metric ->
            StatusGridCard(
                statusMetric = metric,
                backgroundColor = cardBackgroundColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}