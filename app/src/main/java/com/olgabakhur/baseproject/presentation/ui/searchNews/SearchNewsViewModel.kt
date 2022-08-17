package com.olgabakhur.baseproject.presentation.ui.searchNews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.data.model.news.pojo.NewsItem
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onError
import com.olgabakhur.data.util.result.onSuccess
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class SearchNewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _searchNewsResultFlow = MutableSharedFlow<Result<NewsItem>>()
    val searchNewsResultFlow = _searchNewsResultFlow.asSharedFlow()

    var searchNewsPage = 1
    var searchNewsItem: NewsItem? = null

    fun searchNews(searchQuery: String) {
        searchNewsPage = 1
        searchNewsItem = null

        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = newsInteractor.searchNews(
                searchQuery = searchQuery,
                pageNumber = searchNewsPage
            )
            handleSearchNewsResponse(result)
        }
    }

    private suspend fun handleSearchNewsResponse(result: Result<NewsItem>) {
        result
            .onSuccess { newsItem ->
                searchNewsPage++

                if (searchNewsItem == null) {
                    searchNewsItem = newsItem
                } else {
                    val oldArticles = searchNewsItem?.articles?.toMutableList()
                        ?: emptyList<Article>().toMutableList()

                    val newArticles = newsItem.articles
                    oldArticles.addAll(newArticles)

                    val updatedNewsItem = newsItem.copy(
                        articles = oldArticles.toList(),
                        status = newsItem.status,
                        totalResults = newsItem.totalResults
                    )

                    searchNewsItem = updatedNewsItem
                }

                searchNewsItem?.let {
                    _searchNewsResultFlow.emit(Result.Success(it))
                }
            }

            .onError {
                _searchNewsResultFlow.emit(result)
            }
    }
}