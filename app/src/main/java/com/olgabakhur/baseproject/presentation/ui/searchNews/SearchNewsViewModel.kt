package com.olgabakhur.baseproject.presentation.ui.searchNews

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.domain.interactors.NewsInteractor
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onError
import com.olgabakhur.data.util.result.onSuccess
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class SearchNewsViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _searchNewsShFlow = MutableSharedFlow<com.olgabakhur.data.util.result.Result<NewsItem>>(replay = 1)
    val searchNewsShFlow = _searchNewsShFlow.asSharedFlow()

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

    private suspend fun handleSearchNewsResponse(result: com.olgabakhur.data.util.result.Result<NewsItem>) {
        result
            .onSuccess { newsItem ->
                searchNewsPage++

                if (searchNewsItem == null) {
                    searchNewsItem = newsItem
                } else {
                    val oldArticles = searchNewsItem?.articles?.toMutableList() ?:
                    emptyList<Article>().toMutableList()

                    val newArticles = newsItem.articles
                    oldArticles.addAll(newArticles)

                    val updatedNewsItem = newsItem.copy(
                        articles = oldArticles.toList(),
                        status = newsItem.status,
                        totalResults = newsItem.totalResults
                    )

                    searchNewsItem = updatedNewsItem
                }

                Log.d("TAGGG", searchNewsItem?.articles?.size.toString())
                searchNewsItem?.let {
                    _searchNewsShFlow.emit(com.olgabakhur.data.util.result.Result.Success(it))
                }
            }

            .onError {
                _searchNewsShFlow.emit(com.olgabakhur.data.util.result.Result.Error(it))
            }
    }
}