package com.olgabakhur.domain.usecases

import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.safecall.SafeIoCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetErrorsUseCase @Inject constructor() {

    fun getApplicationErrorsFlow(): Flow<ApplicationError> =
        merge(
            /* merge errors from multiple sources */
            SafeIoCall.ioErrorsFlow
        )
}