package com.olgabakhur.data.util.safeCall

import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.error.toApplicationError
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SafeIoCall {

    private val _ioErrorFlow = MutableSharedFlow<ApplicationError>(replay = 0)
    val ioErrorsFlow = _ioErrorFlow.asSharedFlow()

    suspend fun <T> doSafeIoCall(ioCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(ioCall.invoke())

        } catch (throwable: Throwable) {
            val appError = throwable.toApplicationError()

            _ioErrorFlow.emit(appError)
            return Result.Error(appError)
        }
    }
}