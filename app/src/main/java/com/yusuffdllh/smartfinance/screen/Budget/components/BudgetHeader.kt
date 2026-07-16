package com.yusuffdllh.smartfinance.screen.budget.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun BudgetHeader(

    navController: NavController

) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)

    ) {

        //---------------------------------------
        // Back
        //---------------------------------------

        IconButton(

            onClick = {

                navController.popBackStack()

            },

            modifier = Modifier.align(Alignment.CenterStart)

        ) {

            Icon(

                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,

                contentDescription = "Back",

                tint = TextPrimary

            )

        }

        //---------------------------------------
        // Title
        //---------------------------------------

        Text(

            text = "Budget",

            modifier = Modifier.align(Alignment.Center),

            color = TextPrimary,

            fontWeight = FontWeight.Bold,

            fontSize = 28.sp

        )

    }

}