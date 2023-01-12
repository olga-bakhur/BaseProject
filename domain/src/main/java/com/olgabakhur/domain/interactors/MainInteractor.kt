package com.olgabakhur.domain.interactors

import com.olgabakhur.domain.usecases.GetErrorsUseCase
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val getErrorsUseCase: GetErrorsUseCase
) {

    fun getApplicationErrors() = getErrorsUseCase.getApplicationErrorsFlow()
}