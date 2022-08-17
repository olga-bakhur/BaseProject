package com.olgabakhur.baseproject.presentation.ui.article

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.domain.interactors.NewsInteractor
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _saveArticleResultFlow: MutableSharedFlow<Result<Long>> =
        MutableSharedFlow()
    val saveArticleResultFlow: SharedFlow<Result<Long>> = _saveArticleResultFlow.asSharedFlow()


    fun saveArticle(article: Article) {
        viewModelScope.launch {
            val result = newsInteractor.insertArticle(article)
            _saveArticleResultFlow.emit(result)
        }
    }
}