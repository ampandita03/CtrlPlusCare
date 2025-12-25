package com.findmydoctor.ctrlpluscare.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginScreen
import com.findmydoctor.ctrlpluscare.ui.screens.logintypechoose.LoginTypeChose
import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreen
import com.findmydoctor.ctrlpluscare.ui.screens.welcomescreen.WelcomeScreen

@Composable
fun MainNavigation(){
    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        NavHost(
            navController = navController,
            startDestination = AppRoute.Login.route
        ){
            composable(AppRoute.Splash.route){
                SplashScreen(navController)
            }

            composable(AppRoute.Welcome.route) {
                WelcomeScreen(navController)
            }

            composable (AppRoute.LoginTypeChose.route){
                LoginTypeChose(navController)
            }

            composable(AppRoute.Login.route) {
                LoginScreen()
            }
        }
    }
}