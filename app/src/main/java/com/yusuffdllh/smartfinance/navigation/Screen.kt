package com.yusuffdllh.smartfinance.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Dashboard : Screen("dashboard")

    data object Transaction : Screen("transaction")

    data object AddTransaction : Screen("add_transaction?id={id}") {
        fun createRoute(id: Long? = null) = if (id != null) "add_transaction?id=$id" else "add_transaction"
    }

    data object Budget : Screen("budget")

    data object ScheduledBill : Screen("scheduled_bill")

    data object Analytics : Screen("analytics")

    data object Profile : Screen("profile")

    data object Account : Screen("account_settings")

    data object Success : Screen("success")

    data object Security : Screen("security")

    data object About : Screen("about")

    data object Backup : Screen("backup")

    data object Notification : Screen("notification_settings")
}