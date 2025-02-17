package com.example.comics.presentation

import android.app.Application
import com.example.comics.util.homeModule
import com.example.comics.util.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(homeModule, networkModules))
        }
    }
}