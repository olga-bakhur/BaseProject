package com.olgabakhur.baseproject.presentation.ui.savedNews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _savedNewsFlow = MutableSharedFlow<Result<Flow<List<Article>>>>(replay = 1)
    val savedNewsFlow = _savedNewsFlow.asSharedFlow()

    private val _deleteArticleFlow = MutableSharedFlow<Result<Int>>(replay = 0)
    val deleteArticleFlow = _deleteArticleFlow.asSharedFlow()

    private var articleToDelete: Article? = null

    private val _restoreArticleFlow = MutableSharedFlow<Result<Long>>(replay = 0)
    val restoreArticleFlow = _restoreArticleFlow.asSharedFlow()

    fun getSavedNews() = viewModelScope.launchWithLoading(Dispatchers.IO) {
        val result = newsInteractor.getSavedArticles()
        _savedNewsFlow.emit(result)
    }

    fun deleteArticle(article: Article) {
        articleToDelete = article

        viewModelScope.launch(Dispatchers.IO) {
            val result = newsInteractor.deleteArticle(article)
            _deleteArticleFlow.emit(result)
        }
    }

    fun restoreArticle() = viewModelScope.launch(Dispatchers.IO) {
        articleToDelete?.let { article ->
            val result = newsInteractor.insertArticle(article)
            _restoreArticleFlow.emit(result)
        }
    }
}