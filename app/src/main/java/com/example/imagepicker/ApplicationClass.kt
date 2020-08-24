package com.example.imagepicker

import android.app.Application
import com.example.imagepicker.di.AppModule.appModule
import com.example.imagepicker.di.AppModule.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationClass : Application() {

    private var appModules = listOf(
        appModule, viewModel
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ApplicationClass)
            modules(appModules)
        }
    }
}