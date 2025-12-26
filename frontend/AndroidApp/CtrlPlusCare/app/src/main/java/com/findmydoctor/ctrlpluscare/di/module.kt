package com.findmydoctor.ctrlpluscare.di

import com.findmydoctor.ctrlpluscare.data.repository.AuthImplementation
import com.findmydoctor.ctrlpluscare.data.repository.DoctorImplementation
import com.findmydoctor.ctrlpluscare.data.repository.SlotsImplementation
import com.findmydoctor.ctrlpluscare.domain.interfaces.AuthInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.PatientInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface
import com.findmydoctor.ctrlpluscare.domain.usecases.BookingUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.DoctorSignUpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAvailableSlotsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.NearbyDoctorUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientProfileUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.PatientSignUpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInOtpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInUseCase
import com.findmydoctor.ctrlpluscare.ui.navigation.MainViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile.DoctorProfileScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorsignup.DoctorSignUpViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingconfirmed.BookingConfirmViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.BookingScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup.PatientSignUpViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreenViewModel
import com.findmydoctor.ctrlpluscare.utils.GenerateFCMToken
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val module = module {
    //viewModels
    viewModel {
        SplashScreenViewModel(get(),get())
    }
    viewModel {
        LoginViewModel(get(),get(),get())
    }
    viewModel {
        PatientProfileScreenViewModel(get())
    }
    viewModel {
        PatientHomeScreenViewModel(get(),get())
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
        MainViewModel(get())
    }
    viewModel {
        DoctorProfileScreenViewModel(get())
    }
    viewModel {
        DoctorSignUpViewModel(get(),get())
    }

    //repositories
    single<AuthInterface> {
        AuthImplementation(get(),get())
    }
    single<PatientInterface> {
        DoctorImplementation(get(),get())
    }
    single<SlotsInterface> {
        SlotsImplementation(get(),get())
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
}