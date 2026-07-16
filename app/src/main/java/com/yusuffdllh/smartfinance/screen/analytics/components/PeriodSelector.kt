package com.yusuffdllh.smartfinance.screen.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun PeriodSelector(

    selected: String,

    onWeeklyClick: () -> Unit,

    onMonthlyClick: () -> Unit,

    onYearlyClick: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Surface,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(4.dp),

        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {

        PeriodItem(

            modifier = Modifier.weight(1f),

            title = "Mingguan",

            selected = selected == "weekly",

            onClick = onWeeklyClick

        )

        PeriodItem(

            modifier = Modifier.weight(1f),

            title = "Bulanan",

            selected = selected == "monthly",

            onClick = onMonthlyClick

        )

        PeriodItem(

            modifier = Modifier.weight(1f),

            title = "Tahunan",

            selected = selected == "yearly",

            onClick = onYearlyClick

        )

    }

}

@Composable
private fun PeriodItem(

    modifier: Modifier = Modifier,

    title: String,

    selected: Boolean,

    onClick: () -> Unit

) {

    Box(

        modifier = modifier
            .height(48.dp)
            .background(

                color =
                    if (selected)
                        Primary
                    else
                        Surface,

                shape = RoundedCornerShape(14.dp)

            )
            .clickable {

                onClick()

            },

        contentAlignment = Alignment.Center

    ) {

        Text(

            text = title,

            color =
                if (selected)
                    TextPrimary
                else
                    TextSecondary,

            fontSize = 15.sp,

            fontWeight =
                if (selected)
                    FontWeight.Bold
                else
                    FontWeight.Medium

        )

    }

}