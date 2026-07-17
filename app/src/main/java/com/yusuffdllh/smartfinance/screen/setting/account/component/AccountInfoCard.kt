package com.yusuffdllh.smartfinance.screen.settings.account.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.model.User
import com.yusuffdllh.smartfinance.ui.theme.Divider
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun AccountInfoCard(

    user: User

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

                text = "Informasi Akun",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = TextPrimary

            )

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            AccountInfoItem(

                title = "Nama Lengkap",

                value = user.username

            )

            HorizontalDivider(

                color = Divider

            )

            AccountInfoItem(

                title = "Email",

                value = user.email

            )

            HorizontalDivider(

                color = Divider

            )

            AccountInfoItem(

                title = "Nomor Telepon",

                value = user.phone

            )

            HorizontalDivider(

                color = Divider

            )

            AccountInfoItem(

                title = "Tanggal Lahir",

                value = user.birthDate

            )

            HorizontalDivider(

                color = Divider

            )

            AccountInfoItem(

                title = "Jenis Kelamin",

                value = user.gender

            )

        }

    }

}