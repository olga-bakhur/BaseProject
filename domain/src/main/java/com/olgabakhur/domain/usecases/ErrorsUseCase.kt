package com.olgabakhur.domain.usecases

import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.safeCall.SafeIoCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class ErrorsUseCase @Inject constructor() {

    fun getApplicationErrors(): Flow<ApplicationError> =
        merge(
            /* merge errors from multiple sources */
            SafeIoCall.ioErrorsFlow
        )
}