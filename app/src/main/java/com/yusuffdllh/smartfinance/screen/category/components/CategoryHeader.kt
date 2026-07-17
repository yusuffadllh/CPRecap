package com.yusuffdllh.smartfinance.screen.category.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun CategoryHeader(

    onBackClick: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically

    ) {

        //-----------------------------------
        // Back Button
        //-----------------------------------

        IconButton(

            onClick = {

                onBackClick()

            }

        ) {

            Icon(

                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,

                contentDescription = "Kembali",

                tint = TextPrimary

            )

        }

        Spacer(modifier = Modifier.width(8.dp))

        //-----------------------------------
        // Title
        //-----------------------------------

        Column(

            modifier = Modifier.weight(1f)

        ) {

            Text(

                text = "Kategori",

                color = TextPrimary,

                fontSize = 28.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = "Kelola kategori transaksi",

                color = TextSecondary,

                fontSize = 14.sp

            )

        }

    }

}