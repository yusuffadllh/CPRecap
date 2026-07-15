package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun SaveTransactionButton(

    selectedIncome: Boolean,

    onClick: () -> Unit

) {

    Button(

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor = if (selectedIncome) Primary else Danger,

            contentColor = TextPrimary

        )

    ) {

        Text(

            text = if (selectedIncome)
                "Simpan Pemasukan"
            else
                "Simpan Pengeluaran",

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.Bold

        )

    }

}