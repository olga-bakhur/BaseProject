package com.olgabakhur.baseproject.presentation.ui.breakingNews

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.news.pojo.NewsItem
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.interactors.NewsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class BreakingNewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _breakingNewsFlow = MutableSharedFlow<Result<NewsItem>>(replay = 1)
    val breakingNewsFlow = _breakingNewsFlow.asSharedFlow()

    fun getBreakingNews(countryCode: String, pageNumber: Int) {
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = newsInteractor.getBreakingNews(
                countryCode = countryCode,
                pageNumber = pageNumber
            )

            _breakingNewsFlow.emit(result)
        }
    }
}