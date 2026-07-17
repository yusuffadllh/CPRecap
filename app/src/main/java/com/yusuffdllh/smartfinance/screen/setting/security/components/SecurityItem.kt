package com.yusuffdllh.smartfinance.screen.settings.security.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Divider
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun SecurityItem(

    icon: ImageVector,

    title: String,

    trailing: String? = null,

    trailingColor: androidx.compose.ui.graphics.Color = TextSecondary,

    showDivider: Boolean = true,

    onClick: () -> Unit

) {

    Column(

        modifier = Modifier.fillMaxWidth()

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                    onClick()

                }
                .padding(vertical = 16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = icon,

                contentDescription = null,

                tint = TextPrimary

            )

            Spacer(

                modifier = Modifier.width(16.dp)

            )

            Text(

                text = title,

                modifier = Modifier.weight(1f),

                style = MaterialTheme.typography.bodyLarge,

                fontWeight = FontWeight.Medium,

                color = TextPrimary

            )

            if (trailing != null) {

                Text(

                    text = trailing,

                    style = MaterialTheme.typography.bodyMedium,

                    color = trailingColor

                )

                Spacer(

                    modifier = Modifier.width(8.dp)

                )

            }

            Icon(

                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,

                contentDescription = null,

                tint = TextSecondary

            )

        }

        if (showDivider) {

            HorizontalDivider(

                color = Divider

            )

        }

    }

}