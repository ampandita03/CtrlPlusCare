package com.findmydoctor.ctrlpluscare

import android.app.Application
import android.util.Log
import com.findmydoctor.ctrlpluscare.di.appModule
import com.findmydoctor.ctrlpluscare.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            println("Koin Started")
            androidContext(this@MyApplication)
            modules(appModule, module)

        }
    }
}