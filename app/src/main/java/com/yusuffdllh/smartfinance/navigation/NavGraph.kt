package com.yusuffdllh.smartfinance.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yusuffdllh.smartfinance.MainActivity

import androidx.navigation.navDeepLink
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
import com.yusuffdllh.smartfinance.presentation.splash.SplashScreen
import com.yusuffdllh.smartfinance.presentation.success.SuccessScreen
import com.yusuffdllh.smartfinance.presentation.transaction.TransactionListScreen
import com.yusuffdllh.smartfinance.presentation.transaction.AddTransactionScreen
import com.yusuffdllh.smartfinance.presentation.transaction.scheduled.ScheduledBillScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val activity = context as? MainActivity
        val navigateTo = activity?.intent?.getStringExtra("navigate_to")
        if (navigateTo == "transactions") {
            navController.navigate(Screen.Transaction.route)
        }
    }

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

        composable(
            route = Screen.AddTransaction.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getLong("id")?.takeIf { it != -1L }
            AddTransactionScreen(navController = navController, transactionId = transactionId)
        }

        composable(Screen.Budget.route) {
            BudgetScreen(navController)
        }

        composable(Screen.ScheduledBill.route) {
            ScheduledBillScreen(navController)
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

    }
}
