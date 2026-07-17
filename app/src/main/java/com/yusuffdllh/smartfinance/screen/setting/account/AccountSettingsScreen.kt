package com.yusuffdllh.smartfinance.screen.settings.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.data.repository.DummyUserRepository
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.screen.settings.account.components.AccountHeader
import com.yusuffdllh.smartfinance.screen.settings.account.components.AccountInfoCard
import com.yusuffdllh.smartfinance.screen.settings.account.components.AccountMenuCard
import com.yusuffdllh.smartfinance.screen.settings.account.components.AccountProfileCard
import com.yusuffdllh.smartfinance.ui.theme.Background

@Composable
fun AccountSettingsScreen(

    navController: NavController

) {

    val user = DummyUserRepository.user

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 32.dp)

    ) {

        AccountHeader(

            onBackClick = {

                navController.popBackStack()

            }

        )

        Spacer(

            modifier = Modifier.height(24.dp)

        )

        AccountProfileCard(

            user = user

        )

        Spacer(

            modifier = Modifier.height(24.dp)

        )

        AccountInfoCard(

            user = user

        )

        Spacer(

            modifier = Modifier.height(24.dp)

        )

        AccountMenuCard(

            onChangePasswordClick = {

                navController.navigate(Screen.Security.route)

            },

            onLanguageClick = {

                // UI only

            },

            onDeleteAccountClick = {

                // UI only

            }

        )

    }

}