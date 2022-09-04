package com.olgabakhur.domain.interactors

import com.olgabakhur.domain.usecases.ErrorsUseCase
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val errorsUseCase: ErrorsUseCase
) {

    fun getApplicationErrors() = errorsUseCase.getApplicationErrors()
}