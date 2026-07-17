package com.yusuffdllh.smartfinance.screen.settings.account.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun AccountMenuCard(

    onChangePasswordClick: () -> Unit,

    onLanguageClick: () -> Unit,

    onDeleteAccountClick: () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Text(

                text = "Akun",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = TextPrimary

            )

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            AccountMenuItem(

                icon = Icons.Outlined.Lock,

                title = "Ubah Password",

                onClick = onChangePasswordClick

            )

            AccountMenuItem(

                icon = Icons.Outlined.Language,

                title = "Bahasa",

                trailing = "Indonesia",

                onClick = onLanguageClick

            )

            AccountMenuItem(

                icon = Icons.Outlined.DeleteOutline,

                title = "Hapus Akun",

                danger = true,

                showDivider = false,

                onClick = onDeleteAccountClick

            )

        }

    }

}