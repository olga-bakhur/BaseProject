package com.olgabakhur.baseproject.presentation.ui.breakingnews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onSuccess
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BreakingNewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel() {

    // 1. Get all articles
    private val _breakingNewsFlow = MutableSharedFlow<Result<List<Article>>>(replay = 1)
    private val _savedNewsFlow = MutableSharedFlow<Result<Flow<List<Article>>>>(replay = 1)
    private val _errorFlow = MutableSharedFlow<ApplicationError>()
    val errorFlow = _errorFlow.asSharedFlow()

    // 2. Save an article
    private val _saveArticleFlow = MutableSharedFlow<Result<Long>>()
    val saveArticleFlow = _saveArticleFlow.asSharedFlow()

    // 3. Delete an article
    private val _deleteArticleFlow = MutableSharedFlow<Result<Int>>()
    val deleteArticleFlow = _deleteArticleFlow.asSharedFlow()

    var currentNewsCategory: NewsCategory = NewsCategory.BREAKING_NEWS
        private set
    var cachedSavedNewsList: List<Article> = ArrayList()
    var cachedBreakingNewsList: List<Article> = ArrayList()

    // 1. Get all articles
    fun getBreakingNewsWithIsSavedInfo(
        countryCode: String,
        pageNumber: Int
    ): Flow<Result<List<Article>>> {

        getBreakingNews(countryCode, pageNumber)
        getSavedNews()

        val combinedNewsFlow =
            _breakingNewsFlow.combine(_savedNewsFlow) { resultListBreaking, resultFlowListSaved ->
                return@combine when (resultListBreaking) {
                    is Result.Success -> {
                        when (resultFlowListSaved) {
                            is Result.Success -> {
                                val listSavedArticles = resultFlowListSaved.value.first()

                                val newListBreaking =
                                    resultListBreaking.value.map { articleBreaking ->
                                        val isSaved = listSavedArticles.any { articleSaved ->
                                            articleBreaking.url.contentEquals(
                                                articleSaved.url,
                                                true
                                            )
                                        }
                                        articleBreaking.isSaved = isSaved
                                        return@map articleBreaking
                                    }

                                Result.Success(newListBreaking)
                            }

                            is Result.Error -> {
                                _errorFlow.emit(resultFlowListSaved.error)
                                Result.Success(resultListBreaking.value)
                            }
                        }
                    }

                    is Result.Error -> {
                        resultListBreaking
                    }
                }
            }

        cacheBreakingNewsList(combinedNewsFlow)
        return combinedNewsFlow
    }

    private fun cacheBreakingNewsList(flow: Flow<Result<List<Article>>>) {
        runBlocking {
            flow.first().onSuccess {
                cachedBreakingNewsList = it
            }
        }
    }

    private fun getBreakingNews(countryCode: String, pageNumber: Int) =
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = newsInteractor.getBreakingNews(
                countryCode = countryCode,
                pageNumber = pageNumber
            )
            _breakingNewsFlow.emit(result)
        }

    private fun getSavedNews() =
        viewModelScope.launchWithLoading {
            val result = newsInteractor.getSavedArticles()
            _savedNewsFlow.emit(result)

            cacheSavedNewsList(result)
        }

    private fun cacheSavedNewsList(result: Result<Flow<List<Article>>>) {
        result.onSuccess { flow ->
            runBlocking {
                cachedSavedNewsList = flow.first().map { article ->
                    article.isSaved = true
                    article
                }
            }
        }
    }

    // 2. Save an article
    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsInteractor.insertArticle(article)
            _saveArticleFlow.emit(result)
        }
    }

    // 3. Delete an article
    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsInteractor.deleteArticle(article)
            _deleteArticleFlow.emit(result)
        }
    }

    fun getNewsByCategory(category: NewsCategory): List<Article> {
        currentNewsCategory = category

        return if (currentNewsCategory == NewsCategory.BREAKING_NEWS) {
            cachedBreakingNewsList
        } else {
            cachedSavedNewsList
        }
    }
}