package com.findmydoctor.ctrlpluscare.di

import com.findmydoctor.ctrlpluscare.ui.screens.splashscreen.SplashScreenViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val module = module {
    //viewModels
    viewModel {
        SplashScreenViewModel()
    }
}