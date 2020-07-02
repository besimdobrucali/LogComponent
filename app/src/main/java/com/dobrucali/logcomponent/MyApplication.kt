package com.dobrucali.logcomponent

import android.app.Application
import com.dobrucali.logcomponent.di.module.appModule
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin Dependency Injection with modules from [appModule]
        startKoin(this, listOf(appModule))
    }
}