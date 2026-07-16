package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.ChartBlue
import com.yusuffdllh.smartfinance.ui.theme.ChartOrange
import com.yusuffdllh.smartfinance.ui.theme.ChartPurple
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun CategoryDropdown(

    category: String,

    onClick: () -> Unit

) {

    val (icon, color) = getCategoryStyle(category)

    Column {

        Text(

            text = "Kategori",

            style = MaterialTheme.typography.bodyMedium,

            color = TextSecondary,

            fontWeight = FontWeight.Medium

        )

        Spacer(modifier = Modifier.height(8.dp))

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
                    .padding(horizontal = 18.dp, vertical = 18.dp),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Box(

                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                color.copy(alpha = .15f),
                                CircleShape
                            ),

                        contentAlignment = Alignment.Center

                    ) {

                        Icon(

                            imageVector = icon,

                            contentDescription = null,

                            tint = color

                        )

                    }

                    Spacer(modifier = Modifier.size(14.dp))

                    Text(

                        text = category,

                        color = TextPrimary,

                        style = MaterialTheme.typography.bodyLarge,

                        fontWeight = FontWeight.SemiBold

                    )

                }

                Icon(

                    imageVector = Icons.Default.ChevronRight,

                    contentDescription = null,

                    tint = TextSecondary

                )

            }

        }

    }

}

private fun getCategoryStyle(

    category: String

): Pair<ImageVector, Color> {

    return when (category) {

        "Makanan" ->
            Icons.Default.Fastfood to Primary

        "Transportasi" ->
            Icons.Default.DirectionsBus to ChartBlue

        "Belanja" ->
            Icons.Default.ShoppingBag to ChartPurple

        "Tagihan" ->
            Icons.AutoMirrored.Filled.ReceiptLong to ChartOrange

        "Hiburan" ->
            Icons.Default.Movie to Danger

        "Kesehatan" ->
            Icons.Default.LocalHospital to Danger

        "Pendidikan" ->
            Icons.Default.School to ChartBlue

        else ->
            Icons.Default.Fastfood to Primary

    }

}