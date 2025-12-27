package com.findmydoctor.ctrlpluscare.di

import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import com.findmydoctor.ctrlpluscare.ui.TestViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        LocalStorage(androidContext())
    }

    single {
        HttpClient(OkHttp) {

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = true
                        explicitNulls = false
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
    viewModel {
        TestViewModel(get())
    }
}