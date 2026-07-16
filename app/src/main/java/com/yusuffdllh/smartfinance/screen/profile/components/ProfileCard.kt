package com.yusuffdllh.smartfinance.screen.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary
import com.yusuffdllh.smartfinance.ui.theme.Secondary

@Composable
fun ProfileCard(

    name: String,

    email: String

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        ),

        border = BorderStroke(

            1.dp,

            Surface

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Image(

                painter = painterResource(R.drawable.profile),

                contentDescription = null,

                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(
                            2.dp,
                            Primary
                        ),
                        CircleShape
                    )

            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(

                    text = name,

                    color = TextPrimary,

                    fontSize = 26.sp,

                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = email,

                    color = TextSecondary,

                    fontSize = 17.sp

                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    text = "Edit Profil",

                    color = Secondary,

                    fontSize = 17.sp,

                    fontWeight = FontWeight.Medium

                )

            }

        }

    }

}