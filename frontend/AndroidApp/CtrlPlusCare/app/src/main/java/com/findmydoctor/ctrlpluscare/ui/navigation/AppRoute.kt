package com.findmydoctor.ctrlpluscare.ui.navigation

sealed class AppRoute(val route: String) {
    object Splash : AppRoute("splash")
    object Welcome : AppRoute("welcome")

    object LoginTypeChose : AppRoute("login_type_chose")
    object Login : AppRoute("login")
}