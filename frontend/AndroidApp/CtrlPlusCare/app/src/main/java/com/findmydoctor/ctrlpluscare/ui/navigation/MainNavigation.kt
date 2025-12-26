package com.findmydoctor.ctrlpluscare.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorhomescreen.DoctorHomeScreen
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile.DoctorProfileScreen
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorsignup.DoctorSignUpScreen
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginScreen
import com.findmydoctor.ctrlpluscare.ui.screens.logintypechoose.LoginTypeChose
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingconfirmed.BookingConfirmScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.BookingScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientnotificationscreen.PatientNotificationsScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup.PatientSignUpScreen
import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreen
import com.findmydoctor.ctrlpluscare.ui.screens.welcomescreen.WelcomeScreen
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation(viewModel: MainViewModel = koinViewModel()){

    val userRole by viewModel.role.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getRole()
    }
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
         "PATIENT" -> patientBottomBarItems
        "DOCTOR" -> doctorBottomBarItems
        else -> patientBottomBarItems
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
            composable(AppRoute.BookingScreen.route) {
                BookingScreen(navController)
            }
            composable (AppRoute.PatientSignUpScreen.route){
                PatientSignUpScreen(navController)
            }
            composable (AppRoute.BookingConfirmScreen.route){
                BookingConfirmScreen(navController)
            }

            composable(AppRoute.DoctorHomeScreen.route) {
                DoctorHomeScreen(navController)
            }
            composable (AppRoute.DoctorProfileScreen.route){
                DoctorProfileScreen(navController)
            }
            composable (AppRoute.DoctorSignUpScreen.route){
                DoctorSignUpScreen(navController)
            }

        }
    }
}