package com.yusuffdllh.smartfinance.screen.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun LogoutButton(

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()

            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),

            horizontalArrangement = Arrangement.Center,

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = Icons.Outlined.Logout,

                contentDescription = null,

                tint = TextPrimary

            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(

                text = "Keluar",

                color = TextPrimary,

                fontSize = 20.sp,

                fontWeight = FontWeight.Medium

            )

        }

    }

}