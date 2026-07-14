package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun SummaryCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        shape = RoundedCornerShape(22.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            //----------------------------------
            // Donut Placeholder
            //----------------------------------

            Box(
                modifier = Modifier.size(170.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            Color(0xFF2563EB),
                            CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(95.dp)
                        .background(
                            Surface,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Total",
                            color = TextSecondary
                        )

                        Text(
                            "Rp3.250.000",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

            //----------------------------------
            // Legend
            //----------------------------------

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(170.dp)
            ) {

                CategoryItem(
                    "Makanan",
                    "35%",
                    Primary
                )

                CategoryItem(
                    "Transportasi",
                    "20%",
                    Color(0xFF3B82F6)
                )

                CategoryItem(
                    "Belanja",
                    "18%",
                    Color(0xFF8B5CF6)
                )

                CategoryItem(
                    "Tagihan",
                    "15%",
                    Color(0xFFF59E0B)
                )

                CategoryItem(
                    "Hiburan",
                    "12%",
                    Danger
                )

            }

        }

    }

}