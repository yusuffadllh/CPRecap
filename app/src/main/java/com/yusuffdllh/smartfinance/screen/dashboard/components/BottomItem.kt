package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun BottomItem(

    icon: ImageVector,

    title: String,

    selected: Boolean,

    onClick: () -> Unit

) {

    Column(

        modifier = Modifier.clickable {
            onClick()
        },

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Icon(

            imageVector = icon,

            contentDescription = null,

            tint =
                if (selected)
                    Primary
                else
                    TextSecondary

        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(

            text = title,

            fontSize = 11.sp,

            fontWeight =
                if (selected)
                    FontWeight.Bold
                else
                    FontWeight.Normal,

            color =
                if (selected)
                    Primary
                else
                    TextSecondary

        )

    }

}