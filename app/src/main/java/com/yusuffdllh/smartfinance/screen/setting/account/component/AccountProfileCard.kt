package com.yusuffdllh.smartfinance.screen.settings.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.model.User
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun AccountProfileCard(

    user: User,

    onChangePhotoClick: () -> Unit = { }

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Box(

                modifier = Modifier
                    .size(90.dp)
                    .background(
                        Primary.copy(alpha = 0.15f),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = Icons.Default.Person,

                    contentDescription = null,

                    tint = Primary,

                    modifier = Modifier.size(44.dp)

                )

            }

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            Text(

                text = user.username,

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = TextPrimary

            )

            Spacer(

                modifier = Modifier.height(4.dp)

            )

            Text(

                text = user.email,

                style = MaterialTheme.typography.bodyMedium,

                color = TextSecondary

            )

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            Text(

                text = "Ubah Foto",

                style = MaterialTheme.typography.labelLarge,

                color = Primary,

                modifier = Modifier.clickable {

                    onChangePhotoClick()

                }

            )

        }

    }

}