package com.example.mobile_client_app.features.auth.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.features.auth.profile.data.remote.models.StatusMetricResponse

@Composable
fun StatusGridCard(
    statusMetric: StatusMetricResponse,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = statusMetric.title,
                fontSize = 14.sp
            )

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = statusMetric.value,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                statusMetric.subtitle?.let { subtitle ->
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 2.dp, start = 2.dp)
                    )
                }
            }
        }
    }
}