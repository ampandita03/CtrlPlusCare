package com.findmydoctor.ctrlpluscare.ui.navigation

sealed class AppRoute(val route: String) {
    object Splash : AppRoute("splash")
    object Welcome : AppRoute("welcome")
}