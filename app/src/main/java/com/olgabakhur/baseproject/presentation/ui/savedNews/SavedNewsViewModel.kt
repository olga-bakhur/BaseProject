package com.olgabakhur.baseproject.presentation.ui.savedNews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.Article
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    fun getSavedNews() = newsInteractor.getSavedArticles()

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsInteractor.upsert(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsInteractor.deleteArticle(article)
        }
    }
}