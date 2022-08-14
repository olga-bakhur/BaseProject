package com.olgabakhur.domain.util

import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): com.olgabakhur.domain.util.result.Result<T> {
    return try {
        com.olgabakhur.domain.util.result.Result.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.TimeOut)
            is ConnectException -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.NoInternetConnection)
            is UnknownHostException -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.NoInternetConnection)
            is HttpException -> {

                val error = throwable.getCustomHttpError().getApplicationErrorOrNull()
                if (error != null) return com.olgabakhur.domain.util.result.Result.Error(error)

                when (throwable.code()) {
                    HTTP_BAD_REQUEST -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.BadRequest)
                    HTTP_UNAUTHORIZED -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.Unauthorized)
                    HTTP_NOT_FOUND -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.NotFound)
                    HTTP_INTERNAL_ERROR -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.Server)
                    else -> com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.Generic)
                }
            }
            else -> {
                com.olgabakhur.domain.util.result.Result.Error(com.olgabakhur.domain.util.error.ApplicationError.Generic)
            }
        }
    }
}