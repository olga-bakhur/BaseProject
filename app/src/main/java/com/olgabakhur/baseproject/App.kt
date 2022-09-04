package com.olgabakhur.baseproject

import android.app.Application
import com.olgabakhur.baseproject.di.component.AppComponent
import com.olgabakhur.baseproject.di.component.DaggerAppComponent
import com.olgabakhur.data.util.safeCall.NetworkConnectivityManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        application = this
        NetworkConnectivityManager.initConnectivityListener(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set

        lateinit var application: Application
            private set
    }
}