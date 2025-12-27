package com.findmydoctor.ctrlpluscare.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorhomescreen.DoctorHomeScreen
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile.DoctorProfileScreen
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorsignup.DoctorSignUpScreen
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorslotsscreen.DoctorSlotsScreen
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.notification.DoctorNotificationsScreen
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginScreen
import com.findmydoctor.ctrlpluscare.ui.screens.logintypechoose.LoginTypeChose
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingconfirmed.BookingConfirmScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.BookingScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.EmergencyScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencybookingconfirmscreen.EmergencyBookingConfirmScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen.EmergencyDoctorInfoScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientdiscoverypage.PatientDiscoveryPage
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientnotificationscreen.PatientNotificationsScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup.PatientSignUpScreen
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.schedule.PatientScheduleScreen
import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreen
import com.findmydoctor.ctrlpluscare.ui.screens.welcomescreen.WelcomeScreen
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation(viewModel: MainViewModel = koinViewModel()){
    val userRole by viewModel.role.collectAsState()
    val unreadCount by viewModel.unreadCount.collectAsState()




    LaunchedEffect(userRole) {
        Log.d("MainNavigation", "👤 User role updated: [$userRole]")
    }

    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState().value
            ?.destination?.route

    LaunchedEffect(currentRoute) {
        Log.d("MainNavigation", "📍 Current route: $currentRoute")
    }

    val showBottomBar = currentRoute in listOf(
        AppRoute.PatientHomeScreen.route,
        AppRoute.PatientNotificationScreen.route,
        AppRoute.PatientProfileScreen.route,
        AppRoute.DoctorHomeScreen.route,
        AppRoute.DoctorNotificationScreen.route,
        AppRoute.DoctorProfileScreen.route,
        AppRoute.DoctorSlotsScreen.route
    )

    LaunchedEffect(showBottomBar) {
        Log.d("MainNavigation", "⬇️ Show bottom bar: $showBottomBar")
    }

    val bottomBarItems = when (userRole) {
        "PATIENT" -> {
            Log.d("MainNavigation", "🧭 Using PATIENT bottom bar")
            patientBottomBarItems
        }
        "DOCTOR" -> {
            Log.d("MainNavigation", "🧭 Using DOCTOR bottom bar")
            doctorBottomBarItems
        }
        else -> {
            Log.w("MainNavigation", "⚠️ Unknown role, defaulting to PATIENT")
            patientBottomBarItems
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar && bottomBarItems.isNotEmpty()) {
                AppBottomBar(
                    navController = navController,
                    items = bottomBarItems,
                    unreadCount = unreadCount
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
                PatientNotificationsScreen(navController)
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
            composable (AppRoute.PatientDiscoveryPage.route){
                PatientDiscoveryPage(navController)
            }
            composable(AppRoute.PatientScheduleScreen.route) {
                PatientScheduleScreen(navController)
            }
            composable(AppRoute.PatientEmergencyScreen.route) {
                EmergencyScreen(navController)
            }
            composable(AppRoute.EmergencyDoctorInfoScreen.route) {
                EmergencyDoctorInfoScreen(navController)
            }
            composable(AppRoute.EmergencyBookingConfirmScreen.route) {
                EmergencyBookingConfirmScreen(navController)
            }
            composable(AppRoute.DoctorSlotsScreen.route) {
                DoctorSlotsScreen(navController)
            }
            composable (AppRoute.DoctorNotificationScreen.route){
                DoctorNotificationsScreen(navController)
            }
        }
    }
}