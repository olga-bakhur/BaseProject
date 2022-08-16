package com.olgabakhur.domain.util

import com.olgabakhur.domain.util.error.ApplicationError
import com.olgabakhur.domain.util.result.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object SafeApiCall {

    private val _networkErrorsFlow = MutableSharedFlow<ApplicationError>(replay = 0)
    val networkErrorsFlow = _networkErrorsFlow.asSharedFlow()

    suspend fun <T> doSafeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {

            val appError = when (throwable) {
                is SocketTimeoutException -> ApplicationError.TimeOut
                is ConnectException -> ApplicationError.NoInternetConnection
                is UnknownHostException -> ApplicationError.NoInternetConnection
                is HttpException -> {

                    when (throwable.code()) {
                        HTTP_BAD_REQUEST -> ApplicationError.BadRequest                       // 400
                        HTTP_UNAUTHORIZED -> ApplicationError.Unauthorized                    // 401
                        HTTP_FORBIDDEN -> ApplicationError.Forbidden                          // 403
                        HTTP_NOT_FOUND -> ApplicationError.NotFound                           // 404
                        HTTP_GATEWAY_TIMEOUT,                                                 // 504
                        HTTP_CLIENT_TIMEOUT -> ApplicationError.TimeOut                       // 408
                        HTTP_CONFLICT -> ApplicationError.Conflict                            // 409
                        HTTP_UNSUPPORTED_TYPE -> ApplicationError.UnsupportedType             // 415
                        ApplicationError.TOO_MANY_REQUESTS -> ApplicationError.TooManyRequests// 429
                        HTTP_BAD_GATEWAY,                                                     // 502
                        HTTP_INTERNAL_ERROR -> ApplicationError.Server                        // 500

                        else -> ApplicationError.Unknown                                       // -1
                    }
                }
                else -> {
                    ApplicationError.Unknown                                                   // -1
                }
            }
            _networkErrorsFlow.emit(appError)
            return Result.Error(appError)
        }
    }
}