package com.olgabakhur.baseproject.global.di

import android.app.Application
import com.olgabakhur.baseproject.global.di.component.AppComponent
import com.olgabakhur.baseproject.global.di.component.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}