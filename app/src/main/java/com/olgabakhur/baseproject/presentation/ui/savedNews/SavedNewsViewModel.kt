package com.olgabakhur.baseproject.presentation.ui.savedNews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.domain.interactors.NewsInteractor
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _savedNewsResultFlow: MutableSharedFlow<Result<Flow<List<Article>>>> =
        MutableSharedFlow(replay = 1)
    val savedNewsResultFlow: SharedFlow<Result<Flow<List<Article>>>> =
        _savedNewsResultFlow.asSharedFlow()

    private val _deleteArticleResultFlow: MutableSharedFlow<Result<Int>> =
        MutableSharedFlow(replay = 0)
    val deleteArticleResultFlow: SharedFlow<Result<Int>> = _deleteArticleResultFlow.asSharedFlow()

    private var articleToDelete: Article? = null

    private val _restoreArticleResultFlow: MutableSharedFlow<Result<Long>> =
        MutableSharedFlow(replay = 0)
    val restoreArticleResultFlow: SharedFlow<Result<Long>> =
        _restoreArticleResultFlow.asSharedFlow()

    fun getSavedNews() =
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsInteractor.getSavedArticles()
            _savedNewsResultFlow.emit(result)
        }

    fun deleteArticle(article: Article) {
        articleToDelete = article

        viewModelScope.launch(Dispatchers.IO) {
            val result = newsInteractor.deleteArticle(article)
            _deleteArticleResultFlow.emit(result)
        }
    }

    fun restoreArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            articleToDelete?.let { article ->
                val result = newsInteractor.insertArticle(article)
                _restoreArticleResultFlow.emit(result)
            }
        }
    }
}