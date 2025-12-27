package com.findmydoctor.ctrlpluscare.ui.navigation

sealed class AppRoute(val route: String) {
    object Splash : AppRoute("splash")
    object Welcome : AppRoute("welcome")

    object LoginTypeChose : AppRoute("login_type_chose")
    object Login : AppRoute("login")

    object PatientSignUpScreen : AppRoute("patient_sign_up")

    object BookingConfirmScreen : AppRoute("booking_confirm")

    object PatientHomeScreen : AppRoute("patient_home")
    object PatientNotificationScreen : AppRoute("patient_notification")
    object PatientProfileScreen : AppRoute("patient_profile")
    object DoctorInfoScreen : AppRoute("doctor_info_screen")
    object BookingScreen : AppRoute("booking_screen")


    object PatientDiscoveryPage : AppRoute("patient_discovery")

    object PatientEmergencyScreen : AppRoute("emergency")

    object EmergencyDoctorInfoScreen : AppRoute("emergency_doctor_info")
    object EmergencyBookingConfirmScreen : AppRoute("emergency_booking_confirm")
    object PatientScheduleScreen : AppRoute("patient_schedule")
    object DoctorHomeScreen : AppRoute("doctor_home")
    object DoctorSlotsScreen : AppRoute("doctor_slots")
    object DoctorNotificationScreen : AppRoute("doctor_notification")
    object DoctorProfileScreen : AppRoute("doctor_profile")
    object DoctorSignUpScreen : AppRoute("doctor_sign_up")
}