package com.yusuffdllh.smartfinance.screen.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.Border
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun ProfileMenuItem(

    icon: ImageVector,

    title: String,

    modifier: Modifier = Modifier,

    showDivider: Boolean = true,

    trailingText: String? = null,

    onClick: () -> Unit

) {

    Column(

        modifier = modifier
            .fillMaxWidth()
            .clickable {

                onClick()

            }

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),

            verticalAlignment = Alignment.CenterVertically

        ) {

            //------------------------------------
            // Icon
            //------------------------------------

            Icon(

                imageVector = icon,

                contentDescription = null,

                tint = TextPrimary,

                modifier = Modifier.size(28.dp)

            )

            Spacer(modifier = Modifier.width(18.dp))

            //------------------------------------
            // Title
            //------------------------------------

            Text(

                text = title,

                modifier = Modifier.weight(1f),

                color = TextPrimary,

                fontSize = 18.sp,

                fontWeight = FontWeight.Medium

            )

            //------------------------------------
            // Trailing (Tema)
            //------------------------------------

            if (trailingText != null) {

                Text(

                    text = trailingText,

                    color = TextSecondary,

                    fontSize = 15.sp

                )

                Spacer(modifier = Modifier.width(12.dp))

            }

            //------------------------------------
            // Arrow
            //------------------------------------

            Icon(

                imageVector = Icons.Outlined.ChevronRight,

                contentDescription = null,

                tint = TextSecondary

            )

        }

        if (showDivider) {

            HorizontalDivider(

                color = Border

            )

        }

    }

}