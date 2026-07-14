package com.yusuffdllh.smartfinance.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Onboarding : Screen("onboarding")

    data object Login : Screen("login")

    data object Dashboard : Screen("dashboard")

    data object Transaction : Screen("transaction")

    data object AddTransaction : Screen("add_transaction")

    data object Budget : Screen("budget")

    data object Analytics : Screen("analytics")

    data object Profile : Screen("profile")
}