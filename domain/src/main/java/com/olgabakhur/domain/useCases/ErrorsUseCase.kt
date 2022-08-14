package com.olgabakhur.domain.useCases

import com.olgabakhur.domain.util.SafeApiCall
import com.olgabakhur.domain.util.error.ApplicationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class ErrorsUseCase @Inject constructor() {

    /*
       Get errors from multiple sources and merge them into a single Flow.
       This will allow to create a single observer in MainActivity with only one error dialog.
    */

    fun getApplicationErrors(): Flow<ApplicationError> =
        merge(SafeApiCall.networkErrorsFlow)
}