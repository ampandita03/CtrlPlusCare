package com.findmydoctor.ctrlpluscare.di

import com.findmydoctor.ctrlpluscare.data.repository.AuthImplementation
import com.findmydoctor.ctrlpluscare.data.repository.DoctorImplementation
import com.findmydoctor.ctrlpluscare.data.repository.SlotsImplementation
import com.findmydoctor.ctrlpluscare.domain.interfaces.AuthInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.DoctorsInterface
import com.findmydoctor.ctrlpluscare.domain.interfaces.SlotsInterface
import com.findmydoctor.ctrlpluscare.domain.usecases.GetAvailableSlotsUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.NearbyDoctorUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInOtpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.DoctorInfoScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenViewModel
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.ProfileScreenViewModel
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
        ProfileScreenViewModel(get())
    }
    viewModel {
        PatientHomeScreenViewModel(get(),get())
    }
    viewModel {
        DoctorInfoScreenViewModel(get(),get())
    }


    //repositories
    single<AuthInterface> {
        AuthImplementation(get(),get())
    }
    single<DoctorsInterface> {
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

}