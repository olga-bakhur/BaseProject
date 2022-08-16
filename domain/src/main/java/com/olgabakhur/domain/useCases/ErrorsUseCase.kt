package com.olgabakhur.domain.useCases

import com.olgabakhur.domain.util.error.ApplicationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class ErrorsUseCase @Inject constructor() {

    /*
       If the error is generic for the whole application and must be displayed immediately at any
       screen, create a single observer in MainActivity and show it in a dialog.

       Fetch errors from multiple sources and merge them into a single Flow.
    */

    fun getApplicationErrors(): Flow<ApplicationError> =
        merge(
            /* errors from multiple sources */
        )
}