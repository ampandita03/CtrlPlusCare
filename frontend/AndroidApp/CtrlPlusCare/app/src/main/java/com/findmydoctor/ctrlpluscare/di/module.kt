package com.findmydoctor.ctrlpluscare.di

import com.findmydoctor.ctrlpluscare.data.repository.AuthImplementation
import com.findmydoctor.ctrlpluscare.domain.interfaces.AuthInterface
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInOtpUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.SignInUseCase
import com.findmydoctor.ctrlpluscare.ui.screens.login.LoginViewModel
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


    //repositories
    single<AuthInterface> {
        AuthImplementation(get(),get())
    }


    //useCases
    single { SignInUseCase(get()) }
    single { SignInOtpUseCase(get()) }
    single { GenerateFCMToken(get()) }

}