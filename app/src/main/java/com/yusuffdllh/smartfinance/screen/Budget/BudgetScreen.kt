package com.yusuffdllh.smartfinance.screen.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.data.repository.DummyBudgetRepository
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.screen.budget.components.*
import com.yusuffdllh.smartfinance.screen.dashboard.components.BottomNavigationBar
import com.yusuffdllh.smartfinance.ui.theme.Background

@Composable
fun BudgetScreen(

    navController: NavController

) {

    val budgets = DummyBudgetRepository.budgets

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

            //-----------------------------------
            // Header
            //-----------------------------------

            BudgetHeader(

                navController = navController

            )

            Spacer(modifier = Modifier.height(24.dp))

            //-----------------------------------
            // Summary
            //-----------------------------------

            BudgetSummaryCard(

                totalBudget = DummyBudgetRepository.totalBudget(),

                totalSpent = DummyBudgetRepository.totalSpent(),

                percentage = DummyBudgetRepository.totalPercentage()

            )

            Spacer(modifier = Modifier.height(24.dp))

            //-----------------------------------
            // Budget List
            //-----------------------------------

            BudgetList(

                budgets = budgets

            )

            Spacer(modifier = Modifier.height(24.dp))

            //-----------------------------------
            // Button
            //-----------------------------------

            CreateBudgetButton(

                onClick = {

                    // nanti diarahkan ke halaman tambah budget
                    // jika memang ada di Figma

                }

            )

            Spacer(modifier = Modifier.height(24.dp))

        }

        //-----------------------------------
        // Bottom Navigation
        //-----------------------------------

        BottomNavigationBar(

            modifier = Modifier.align(Alignment.BottomCenter),

            selected = "",

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

                navController.navigate(Screen.Profile.route)

            }

        )

    }

}