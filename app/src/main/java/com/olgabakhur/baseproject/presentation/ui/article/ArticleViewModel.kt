package com.olgabakhur.baseproject.presentation.ui.article

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _saveArticleFlow: MutableSharedFlow<Result<Long>> = MutableSharedFlow()
    val saveArticleFlow: SharedFlow<Result<Long>> = _saveArticleFlow.asSharedFlow()

    var article: Article? = null
        private set

    fun saveArticle() {
        article?.let { articleToSave ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = newsInteractor.insertArticle(articleToSave)
                _saveArticleFlow.emit(result)
            }
        }
    }

    fun setArticle(article: Article) {
        this.article = article
    }
}