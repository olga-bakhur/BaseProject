package com.olgabakhur.domain.util

import android.database.sqlite.SQLiteException
import com.olgabakhur.domain.util.error.ApplicationError
import com.olgabakhur.domain.util.result.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SafeDatabaseCall {

    private val _databaseErrorsFlow = MutableSharedFlow<ApplicationError>(replay = 0)
    val databaseErrorsFlow = _databaseErrorsFlow.asSharedFlow()

    suspend fun <T> doSafeDatabaseCall(dbCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(dbCall.invoke())
        } catch (throwable: Throwable) {

            val appError = when (throwable) {
                is SQLiteException -> ApplicationError.DatabaseOperation
                else -> ApplicationError.Unknown
            }

            _databaseErrorsFlow.emit(appError)
            return Result.Error(appError)
        }
    }
}