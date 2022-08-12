package com.olgabakhur.baseproject.presentation.ui.article

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.Article
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsInteractor.upsert(article)
        }
    }
}