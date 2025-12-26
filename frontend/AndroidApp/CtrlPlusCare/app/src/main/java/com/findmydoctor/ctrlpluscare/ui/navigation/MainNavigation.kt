package com.findmydoctor.ctrlpluscare.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginScreen
import com.findmydoctor.ctrlpluscare.ui.screens.logintypechoose.LoginTypeChose
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientnotificationscreen.PatientNotificationsScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileScreen
import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreen
import com.findmydoctor.ctrlpluscare.ui.screens.welcomescreen.WelcomeScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation(){
    val userRole by remember { mutableStateOf(UserRole.PATIENT) }
    LaunchedEffect(Unit) { }
    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState().value
            ?.destination?.route

    val showBottomBar = currentRoute in listOf(
        AppRoute.PatientHomeScreen.route,
        AppRoute.PatientNotificationScreen.route,
        AppRoute.PatientProfileScreen.route,
        AppRoute.DoctorHomeScreen.route,
        AppRoute.DoctorNotificationScreen.route,
        AppRoute.DoctorProfileScreen.route
    )

    val bottomBarItems = when (userRole) {
        UserRole.PATIENT -> patientBottomBarItems
        UserRole.DOCTOR -> doctorBottomBarItems
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar && bottomBarItems.isNotEmpty()) {
                AppBottomBar(
                    navController = navController,
                    items = bottomBarItems
                )
            }
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = AppRoute.Splash.route,
        ) {

            composable(AppRoute.Splash.route) {
                SplashScreen(navController)
            }

            composable(AppRoute.Welcome.route) {
                WelcomeScreen(navController)
            }

            composable(AppRoute.LoginTypeChose.route) {
                LoginTypeChose(navController)
            }

            composable(AppRoute.Login.route) {
                LoginScreen(navController)
            }

            // 🔵 Patient Screens
            composable(AppRoute.PatientHomeScreen.route) {
                PatientHomeScreen(navController)
            }
            composable(AppRoute.PatientNotificationScreen.route) {
                PatientNotificationsScreen()
            }
            composable(AppRoute.PatientProfileScreen.route) {
                PatientProfileScreen(navController)
            }
            composable (AppRoute.DoctorInfoScreen.route){
                DoctorInfoScreen(navController)
            }

        }
    }
}