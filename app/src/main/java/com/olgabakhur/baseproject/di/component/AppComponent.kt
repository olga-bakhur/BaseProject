package com.olgabakhur.baseproject.di.component

import android.app.Application
import com.olgabakhur.baseproject.di.module.ApiModule
import com.olgabakhur.baseproject.di.module.RepositoriesModule
import com.olgabakhur.baseproject.presentation.ui.MainViewModel
import com.olgabakhur.baseproject.presentation.ui.article.ArticleViewModel
import com.olgabakhur.baseproject.presentation.ui.breakingNews.BreakingNewsViewModel
import com.olgabakhur.baseproject.presentation.ui.savedNews.SavedNewsViewModel
import com.olgabakhur.baseproject.presentation.ui.searchNews.SearchNewsViewModel
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
    val breakingNewsViewModel: BreakingNewsViewModel
    val savedNewsViewModel: SavedNewsViewModel
    val searchNewsViewModel: SearchNewsViewModel
    val articleNewsViewModel: ArticleViewModel
}