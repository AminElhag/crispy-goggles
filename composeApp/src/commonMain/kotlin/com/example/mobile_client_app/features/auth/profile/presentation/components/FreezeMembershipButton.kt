package com.example.mobile_client_app.features.auth.profile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.common.component.RoundedCornerButton
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.freeze_membership
import org.jetbrains.compose.resources.stringResource

@Composable
fun FreezeMembershipButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RoundedCornerButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        text = stringResource(Res.string.freeze_membership),
        enabled = enabled,
        textStyle = TextStyle(fontSize = 20.sp),
        colors = ButtonDefaults.outlinedButtonColors(),
        border = BorderStroke(1.dp, Color.Gray),
    )
}