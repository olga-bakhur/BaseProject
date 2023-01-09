package com.olgabakhur.baseproject.presentation.ui.articles

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.interactors.ArticleInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    private val articleInteractor: ArticleInteractor
) : BaseViewModel() {

    private val _articlesFlow = MutableStateFlow<Result<List<Article>>>(Result.Success(emptyList()))
    val articlesFlow = _articlesFlow.asStateFlow()

    fun getArticlesList(
        countryCode: String,
        pageNumber: Int
    ) {
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = articleInteractor.getArticlesList(
                countryCode = countryCode,
                pageNumber = pageNumber
            )

            _articlesFlow.emit(result)
        }
    }
}