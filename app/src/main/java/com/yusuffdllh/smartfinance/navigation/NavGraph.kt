package com.yusuffdllh.smartfinance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.yusuffdllh.smartfinance.screen.analytics.AnalyticsScreen
import com.yusuffdllh.smartfinance.screen.budget.BudgetScreen
import com.yusuffdllh.smartfinance.screen.dashboard.DashboardScreen
import com.yusuffdllh.smartfinance.screen.login.LoginScreen
import com.yusuffdllh.smartfinance.screen.onboarding.OnboardingScreen
import com.yusuffdllh.smartfinance.screen.profile.ProfileScreen
import com.yusuffdllh.smartfinance.screen.splash.SplashScreen
import com.yusuffdllh.smartfinance.screen.transaction.TransactionListScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }

        composable(Screen.Transaction.route) {
            TransactionListScreen()
        }

        composable(Screen.Budget.route) {
            BudgetScreen()
        }

        composable(Screen.Analytics.route) {
            AnalyticsScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

    }

}