package com.yusuffdllh.smartfinance.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.screen.dashboard.components.BottomNavigationBar
import com.yusuffdllh.smartfinance.screen.profile.components.LogoutButton
import com.yusuffdllh.smartfinance.screen.profile.components.ProfileCard
import com.yusuffdllh.smartfinance.screen.profile.components.ProfileHeader
import com.yusuffdllh.smartfinance.screen.profile.components.ProfileMenuCard
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.data.repository.DummyUserRepository

@Composable
fun ProfileScreen(

    navController: NavController

) {
    val user = DummyUserRepository.user

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 110.dp)

        ) {

            //----------------------------------------
            // Header
            //----------------------------------------

            ProfileHeader()

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------
            // Profile Card
            //----------------------------------------

            ProfileCard(

                name = user.username,

                email = user.email

            )

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------
            // Menu
            //----------------------------------------

            ProfileMenuCard(

                onAccountClick = {

                },

                onSecurityClick = {

                },

                onBackupClick = {

                },

                onNotificationClick = {

                },

                onThemeClick = {

                },

                onAboutClick = {

                }

            )

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------
            // Logout
            //----------------------------------------

            LogoutButton(

                onClick = {

                    navController.navigate(Screen.Login.route) {

                        popUpTo(0)

                    }

                }

            )

        }

        //----------------------------------------
        // Bottom Navigation
        //----------------------------------------

        BottomNavigationBar(

            modifier = Modifier.align(Alignment.BottomCenter),

            selected = "profile",

            onHomeClick = {

                navController.navigate(Screen.Dashboard.route)

            },

            onTransactionClick = {

                navController.navigate(Screen.Transaction.route)

            },

            onAddClick = {

                navController.navigate(Screen.AddTransaction.route)

            },

            onAnalyticsClick = {

                navController.navigate(Screen.Analytics.route)

            },

            onProfileClick = {

            }

        )

    }

}