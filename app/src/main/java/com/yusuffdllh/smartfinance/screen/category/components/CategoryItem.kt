package com.yusuffdllh.smartfinance.screen.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun CategoryItem(

    category: Category,

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()

            },

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp
                ),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box(

                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        category.color.copy(alpha = .15f)
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = category.icon,

                    contentDescription = category.name,

                    tint = category.color

                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(

                text = category.name,

                modifier = Modifier.weight(1f),

                color = TextPrimary,

                style = MaterialTheme.typography.bodyLarge

            )

            Icon(

                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,

                contentDescription = null,

                tint = TextSecondary

            )

        }

    }

}