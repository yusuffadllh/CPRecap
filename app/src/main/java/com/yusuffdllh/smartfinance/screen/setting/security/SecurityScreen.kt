package com.yusuffdllh.smartfinance.screen.settings.security

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.data.repository.DummySecurityRepository
import com.yusuffdllh.smartfinance.screen.settings.security.components.LogoutAllButton
import com.yusuffdllh.smartfinance.screen.settings.security.components.SecurityHeader
import com.yusuffdllh.smartfinance.screen.settings.security.components.SecuritySection
import com.yusuffdllh.smartfinance.ui.theme.Background

@Composable
fun SecurityScreen(

    navController: NavController

) {

    // Simpan data dari repository (sesuai kode awal)
    val security = DummySecurityRepository.getSecuritySetting()
    val devices = DummySecurityRepository.getDevices()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 32.dp)

    ) {

        SecurityHeader(

            onBackClick = {

                navController.popBackStack()

            }

        )

        Spacer(

            modifier = Modifier.height(24.dp)

        )

        SecuritySection(

            onChangePasswordClick = {

                // TODO

            }

        )

        Spacer(

            modifier = Modifier.height(24.dp)

        )

        LogoutAllButton(

            onClick = {

                // TODO

            }

        )

    }

}