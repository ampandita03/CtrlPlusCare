package com.findmydoctor.ctrlpluscare.ui.navigation

sealed class AppRoute(val route: String) {
    object Splash : AppRoute("splash")
    object Welcome : AppRoute("welcome")

    object LoginTypeChose : AppRoute("login_type_chose")
    object Login : AppRoute("login")

    object PatientHomeScreen : AppRoute("patient_home")
    object PatientNotificationScreen : AppRoute("patient_notification")
    object PatientProfileScreen : AppRoute("patient_profile")
    object DoctorInfoScreen : AppRoute("doctor_info_screen")

    object DoctorHomeScreen : AppRoute("doctor_home")
    object DoctorNotificationScreen : AppRoute("doctor_notification")
    object DoctorProfileScreen : AppRoute("doctor_profile")
}