package com.example.mobile_client_app.features.membership.main.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipPlan
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.choose_plan
import mobile_client_app.composeapp.generated.resources.price_decimal
import mobile_client_app.composeapp.generated.resources.selected
import org.jetbrains.compose.resources.stringResource


@Composable
fun PlanCard(
    plan: MembershipPlan,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = plan.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = /*if (isSelected) Color(0xFF2E7D32) else*/ MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = plan.commitmentPeriod,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light),
                    color = /*if (isSelected) Color(0xFF2E7D32) else*/ MaterialTheme.colorScheme.onSurface
                )
            }

            // Price
            Text(
                text = "${plan.price}  ${stringResource(Res.string.price_decimal)}",
                style = MaterialTheme.typography.titleMedium,
            )

            // Choose button
            RoundedCornerButton(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                text = if (isSelected) stringResource(Res.string.selected) else stringResource(Res.string.choose_plan),
                percentOfRoundedCornerShape = 50,
                textStyle = MaterialTheme.typography.bodySmall
            )

            // Features
            Text(
                text = plan.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}