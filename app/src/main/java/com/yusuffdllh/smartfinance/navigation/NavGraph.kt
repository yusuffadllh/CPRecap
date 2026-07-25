package com.yusuffdllh.smartfinance.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.yusuffdllh.smartfinance.presentation.analytics.AnalyticsScreen
import com.yusuffdllh.smartfinance.presentation.budget.BudgetScreen
import com.yusuffdllh.smartfinance.presentation.dashboard.DashboardScreen
import com.yusuffdllh.smartfinance.presentation.login.LoginScreen
import com.yusuffdllh.smartfinance.presentation.login.RegisterScreen
import com.yusuffdllh.smartfinance.presentation.profile.ProfileScreen
import com.yusuffdllh.smartfinance.presentation.setting.account.AccountSettingsScreen
import com.yusuffdllh.smartfinance.presentation.setting.about.AboutScreen
import com.yusuffdllh.smartfinance.presentation.setting.backup.BackupScreen
import com.yusuffdllh.smartfinance.presentation.setting.notification.NotificationScreen
import com.yusuffdllh.smartfinance.presentation.setting.security.SecurityScreen
import com.yusuffdllh.smartfinance.presentation.setting.theme.ThemeScreen
import com.yusuffdllh.smartfinance.presentation.splash.SplashScreen
import com.yusuffdllh.smartfinance.presentation.success.SuccessScreen
import com.yusuffdllh.smartfinance.presentation.transaction.TransactionListScreen
import com.yusuffdllh.smartfinance.presentation.transaction.AddTransactionScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.Transaction.route) {
            TransactionListScreen(navController = navController)
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(navController = navController)
        }

        composable(Screen.Budget.route) {
            BudgetScreen(navController)
        }

        composable(Screen.Analytics.route) {
            AnalyticsScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.Success.route) {
            SuccessScreen(navController)
        }

        composable(Screen.Account.route) {
            AccountSettingsScreen(navController)
        }

        composable(Screen.Security.route) {
            SecurityScreen(navController)
        }

        composable(Screen.About.route) {
            AboutScreen(navController)
        }

        composable(Screen.Backup.route) {
            BackupScreen(navController)
        }

        composable(Screen.Notification.route) {
            NotificationScreen(navController)
        }

        composable(Screen.Theme.route) {
            ThemeScreen(navController)
        }
    }
}
