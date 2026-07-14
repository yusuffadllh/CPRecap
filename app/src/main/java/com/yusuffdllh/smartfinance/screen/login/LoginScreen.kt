package com.yusuffdllh.smartfinance.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.components.*
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun LoginScreen(
    navController: NavController
) {

    var email by remember {

        mutableStateOf("")

    }

    var password by remember {

        mutableStateOf("")

    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Image(

            painter = painterResource(R.drawable.logo_cprecap),

            contentDescription = null,

            modifier = Modifier.size(130.dp)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(

            text = "Selamat Datang",

            color = TextPrimary,

            fontSize = 28.sp,

            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(

            text = "Masuk untuk melanjutkan",

            color = TextSecondary

        )

        Spacer(modifier = Modifier.height(32.dp))

        AppTextField(

            value = email,

            onValueChange = {

                email = it

            },

            label = "Email",

            placeholder = "Masukkan email"

        )

        Spacer(modifier = Modifier.height(18.dp))

        PasswordTextField(

            value = password,

            onValueChange = {

                password = it

            }

        )

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(

            modifier = Modifier.align(Alignment.End),

            onClick = {

            }

        ) {

            Text(

                "Lupa Password?",

                color = Primary

            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(

            text = "Masuk",

            onClick = {

                navController.navigate(Screen.Dashboard.route)

            }

        )

        Spacer(modifier = Modifier.height(30.dp))

        DividerText()

        Spacer(modifier = Modifier.height(24.dp))

        SocialButton(

            icon = R.drawable.ic_google,

            text = "Masuk dengan Google"

        ) {

        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {

            Text(

                "Belum punya akun? ",

                color = TextSecondary

            )

            Text(

                "Daftar",

                color = Primary,

                fontWeight = FontWeight.Bold

            )

        }

    }

}