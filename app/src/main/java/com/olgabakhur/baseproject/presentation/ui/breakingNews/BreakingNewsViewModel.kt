package com.olgabakhur.baseproject.presentation.ui.breakingNews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.domain.interactors.NewsInteractor
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onError
import com.olgabakhur.data.util.result.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreakingNewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _breakingNewsResultFlow = MutableSharedFlow<Result<NewsItem>>(replay = 1)
    val breakingNewsResultFlow = _breakingNewsResultFlow.asSharedFlow()

    var breakingNewsPage = 1
    var breakingNewsItem: NewsItem? = null

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsInteractor.getBreakingNews(
                countryCode = countryCode,
                pageNumber = breakingNewsPage
            )

            handleBreakingNewsResponse(result)
        }
    }

    private suspend fun handleBreakingNewsResponse(result: Result<NewsItem>) {
        result
            .onSuccess { newsItem ->
                breakingNewsPage++

                if (breakingNewsItem == null) {
                    breakingNewsItem = newsItem
                } else {
                    val oldArticles = breakingNewsItem?.articles?.toMutableList()
                        ?: emptyList<Article>().toMutableList()

                    val newArticles = newsItem.articles
                    oldArticles.addAll(newArticles)

                    val updatedNewsItem = newsItem.copy(
                        articles = oldArticles.toList(),
                        status = newsItem.status,
                        totalResults = newsItem.totalResults
                    )

                    breakingNewsItem = updatedNewsItem
                }

                breakingNewsItem?.let {
                    _breakingNewsResultFlow.emit(Result.Success(it))
                }
            }

            .onError {
                _breakingNewsResultFlow.emit(result)
            }
    }
}