package com.olgabakhur.baseproject

import android.app.Application
import com.olgabakhur.baseproject.di.component.AppComponent
import com.olgabakhur.baseproject.di.component.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set

        lateinit var application: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        application = this
    }
}