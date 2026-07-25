package com.yusuffdllh.smartfinance.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Dashboard : Screen("dashboard")

    data object Transaction : Screen("transaction")

    data object AddTransaction : Screen("add_transaction")

    data object Budget : Screen("budget")

    data object Analytics : Screen("analytics")

    data object Profile : Screen("profile")

    data object Account : Screen("account_settings")

    data object Success : Screen("success")

    data object Security : Screen("security")

    data object About : Screen("about")

    data object Backup : Screen("backup")

    data object Notification : Screen("notification_settings")

    data object Theme : Screen("theme_settings")
}