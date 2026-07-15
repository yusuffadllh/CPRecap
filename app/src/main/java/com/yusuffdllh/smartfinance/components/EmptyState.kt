package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun EmptyState(

    title: String,

    description: String

) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Icon(

            imageVector = Icons.Outlined.ReceiptLong,

            contentDescription = null,

            tint = Primary,

            modifier = Modifier.size(72.dp)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(

            text = title,

            style = MaterialTheme.typography.titleMedium,

            color = TextPrimary

        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(

            text = description,

            color = TextSecondary

        )

    }

}