package com.example.mobile_client_app.features.auth.profile.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceUnknown
import androidx.compose.material.icons.filled.Fitbit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Scale
import androidx.compose.ui.graphics.vector.ImageVector

enum class HeathMetricIcon(val id: Int) {
    Weight(0), Height(1), BMI(2), BodyFat(3);

    companion object {
        fun fromId(id: Int?): HeathMetricIcon? {
            return HeathMetricIcon.entries.find { it.id == id }
        }
    }
}

fun getIcon(heathMetrics: HeathMetricIcon?): ImageVector {
    return when (heathMetrics) {
        HeathMetricIcon.Weight -> Icons.Default.FitnessCenter
        HeathMetricIcon.Height -> Icons.Default.Height
        HeathMetricIcon.BMI -> Icons.Default.Scale
        HeathMetricIcon.BodyFat -> Icons.Default.Fitbit
        null -> Icons.Default.DeviceUnknown
    }
}