package com.olgabakhur.data.util.safecall

import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SafeIoCall {

    private val _ioErrorsFlow = MutableSharedFlow<ApplicationError>(replay = 0)
    val ioErrorsFlow = _ioErrorsFlow.asSharedFlow()

    suspend fun <T> doSafeIoCall(ioCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(ioCall.invoke())

        } catch (throwable: Throwable) {
            val appError = ApplicationError.getApplicationError(throwable)

            _ioErrorsFlow.emit(appError)
            return Result.Error(appError)
        }
    }
}