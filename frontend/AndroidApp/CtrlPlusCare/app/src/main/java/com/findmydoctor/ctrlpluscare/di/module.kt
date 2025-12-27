package com.findmydoctor.ctrlpluscare.di

import com.findmydoctor.ctrlpluscare.data.repository.AuthImplementation
import com.findmydoctor.ctrlpluscare.data.repository.DoctorImplementation
import com.findmydoctor.ctrlpluscare.data.repository.PatientImplementation
import com.findmydoctor.ctrlpluscare.data.repository.SlotsImplementation
import com.findmydoctor.ctrlpluscare.domain.interfaces.AuthInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface
import com.findmydoctor.ctrlpluscare.domain.usecases.AllDoctorUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.BookingUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.CancelAppointmentsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.DoctorSignUpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.EmergencyBookingUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAppointmentsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAvailableSlotsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetDoctorPatientsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetDoctorProfileUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetNotificationUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.MarkAllNotificationUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.MarkNotificationUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.NearbyDoctorUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientProfileUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientSignUpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SetSlotsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInOtpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.UpdateDoctorProfileUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.UpdatePatientProfileUseCase
import com.findmydoctor.ctrlpluscare.ui.navigation.MainViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorhomescreen.DoctorHomeScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile.DoctorProfileScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorsignup.DoctorSignUpViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorslotsscreen.DoctorSlotsScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingconfirmed.BookingConfirmViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.BookingScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.EmergencyScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencybookingconfirmscreen.EmergencyBookingConfirmViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen.EmergencyDoctorInfoViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientdiscoverypage.PatientDiscoveryViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientnotificationscreen.PatientNotificationScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup.PatientSignUpViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.schedule.PatientScheduleScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreenViewModel
import com.findmydoctor.ctrlpluscare.utils.GenerateFCMToken
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import kotlin.math.sin

val module = module {
    //viewModels
    viewModel {
        SplashScreenViewModel(get(),get())
    }
    viewModel {
        LoginViewModel(get(),get(),get())
    }
    viewModel {
        PatientProfileScreenViewModel(get(),get(),get())
    }
    viewModel {
        PatientHomeScreenViewModel(get(),get(),get())
    }
    viewModel {
        DoctorInfoScreenViewModel(get(),get())
    }
    viewModel {
        BookingScreenViewModel(get(),get())
    }
    viewModel {
        PatientSignUpViewModel(get(),get())
    }
    viewModel {
        BookingConfirmViewModel(get())
    }
    viewModel {
        MainViewModel(get(),get())
    }
    viewModel {
        DoctorProfileScreenViewModel(get(),get(),get())
    }
    viewModel {
        DoctorSignUpViewModel(get(),get())
    }
    viewModel {
        PatientDiscoveryViewModel(get(),get())
    }
    viewModel {
        PatientScheduleScreenViewModel(get(),get(),get())
    }
    viewModel {
        EmergencyScreenViewModel(get(),get())
    }
    viewModel {
        EmergencyDoctorInfoViewModel(get(), get())
    }
    viewModel {
        EmergencyBookingConfirmViewModel(get())
    }
    viewModel {
        DoctorHomeScreenViewModel(get(),get(),get())
    }
    viewModel {
        DoctorSlotsScreenViewModel(get(),get(),get())
    }
    viewModel {
        PatientNotificationScreenViewModel(get(),get(),get())
    }

    //repositories
    single<AuthInterface> {
        AuthImplementation(get(),get())
    }
    single<PatientInterface> {
        PatientImplementation(get(),get())
    }
    single<SlotsInterface> {
        SlotsImplementation(get(),get())
    }
    single<DoctorsInterface> {
        DoctorImplementation(get(), get())
    }


    //useCases
    single { SignInUseCase(get()) }
    single { SignInOtpUseCase(get()) }
    single { GenerateFCMToken(get()) }
    single { NearbyDoctorUseCase(get()) }
    single { GetAvailableSlotsUseCase(get()) }
    single { PatientSignUpUseCase(get()) }
    single { BookingUseCase(get()) }
    single { DoctorSignUpUseCase(get()) }
    single { PatientProfileUseCase(get()) }
    single { AllDoctorUseCase(get()) }
    single { UpdatePatientProfileUseCase(get()) }
    single { GetAppointmentsUseCase(get()) }
    single { CancelAppointmentsUseCase(get()) }
    single { EmergencyBookingUseCase(get()) }
    single { GetDoctorProfileUseCase(get()) }
    single { GetDoctorPatientsUseCase(get()) }
    single { SetSlotsUseCase(get()) }
    single { UpdateDoctorProfileUseCase(get()) }
    single { GetNotificationUseCase(get()) }
    single { MarkNotificationUseCase(get()) }
    single { MarkAllNotificationUseCase(get()) }
}