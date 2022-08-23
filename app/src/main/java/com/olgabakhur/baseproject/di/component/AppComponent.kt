package com.olgabakhur.baseproject.di.component

import android.app.Application
import com.olgabakhur.baseproject.di.module.ApiModule
import com.olgabakhur.baseproject.di.module.RepositoriesModule
import com.olgabakhur.baseproject.presentation.ui.MainViewModel
import com.olgabakhur.baseproject.presentation.ui.breakingNews.BreakingNewsViewModel
import com.olgabakhur.baseproject.presentation.ui.signIn.SignInViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RepositoriesModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    val mainViewModel: MainViewModel
    val signInViewModel: SignInViewModel
    val breakingNewsViewModel: BreakingNewsViewModel
}