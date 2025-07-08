package com.example.mobile_client_app.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.ic_broken_gym_tools
import mobile_client_app.composeapp.generated.resources.try_again
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FullScreenError(
    errorMessage: String,
    onRetry: () -> Unit,
    errorIcon: ImageVector? = null // Use platform-specific icons
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // For real implementation, use platform-specific vector resources
        // errorIcon?.let {
        //     Icon(
        //         imageVector = it,
        //         contentDescription = "Error",
        //         modifier = Modifier.size(64.dp),
        //         tint = Color.Red
        //     )
        // }
        Image(
            painter = painterResource(Res.drawable.ic_broken_gym_tools),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp),
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer)
        )

        Text(
            text = "Oops!",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {

                },
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(50)),
            shape = RoundedCornerShape(25),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.scrim
            )
        ) {
            Text(
                text = stringResource(Res.string.try_again),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}