package com.dobrucali.logcomponent.di.module

import com.dobrucali.logcomponent.di.ILogger
import com.dobrucali.logcomponent.di.ShowLogger
import com.dobrucali.logcomponent.main.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Application dependency module, initiated by Koin in [MyApplication]
 */
val appModule = module {

    // Factories
    single<ILogger> { ShowLogger() }

    // View Models
    viewModel { MainViewModel(get()) }

}