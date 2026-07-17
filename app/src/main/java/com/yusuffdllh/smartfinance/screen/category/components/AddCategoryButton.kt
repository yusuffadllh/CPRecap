package com.yusuffdllh.smartfinance.screen.category.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun AddCategoryButton(

    onClick: () -> Unit

) {

    Button(

        onClick = {

            onClick()

        },

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor = Primary

        )

    ) {

        Icon(

            imageVector = Icons.Default.Add,

            contentDescription = null,

            tint = TextPrimary

        )

        Text(

            text = "Tambah Kategori",

            modifier = Modifier.padding(start = 8.dp),

            color = TextPrimary,

            fontSize = 16.sp,

            fontWeight = FontWeight.SemiBold

        )

    }

}