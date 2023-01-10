package com.olgabakhur.data.util.error

import android.database.sqlite.SQLiteException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ApplicationError(
    private val throwable: Throwable
) {
    // Generic errors
    data class Generic(val throwable: Throwable) : ApplicationError(throwable)

    // Network errors
    data class NoInternetConnection(val throwable: Throwable) : ApplicationError(throwable)
    data class SSLException(val throwable: Throwable) : ApplicationError(throwable)

    // Http errors
    data class BadRequest(val throwable: Throwable) : ApplicationError(throwable)
    data class Unauthorized(val throwable: Throwable) : ApplicationError(throwable)
    data class Forbidden(val throwable: Throwable) : ApplicationError(throwable)
    data class NotFound(val throwable: Throwable) : ApplicationError(throwable)
    data class NotAcceptable(val throwable: Throwable) : ApplicationError(throwable)
    data class TimeOut(val throwable: Throwable) : ApplicationError(throwable)
    data class Conflict(val throwable: Throwable) : ApplicationError(throwable)
    data class UnsupportedType(val throwable: Throwable) : ApplicationError(throwable)
    data class TooManyRequests(val throwable: Throwable) : ApplicationError(throwable)
    data class UnknownSession(val throwable: Throwable) : ApplicationError(throwable)
    data class ServerError(val throwable: Throwable) : ApplicationError(throwable)

    // Database errors
    data class DatabaseOperationException(val throwable: Throwable) : ApplicationError(throwable)

    fun getCauseThrowable(): Throwable = throwable

    companion object {
        private const val TOO_MANY_REQUESTS = 429
        private const val UNKNOWN_SESSION = 1001

        fun getApplicationError(throwable: Throwable): ApplicationError =
            when (throwable) {
                is SocketTimeoutException -> TimeOut(throwable)
                is ConnectException,
                is UnknownHostException -> NoInternetConnection(throwable)
                is javax.net.ssl.SSLException -> SSLException(throwable)
                is HttpException -> {
                    when (throwable.code()) {
                        HttpURLConnection.HTTP_BAD_REQUEST -> BadRequest(throwable)                       // 400
                        HttpURLConnection.HTTP_UNAUTHORIZED -> Unauthorized(throwable)                    // 401
                        HttpURLConnection.HTTP_FORBIDDEN -> Forbidden(throwable)                          // 403
                        HttpURLConnection.HTTP_NOT_FOUND -> NotFound(throwable)                           // 404
                        HttpURLConnection.HTTP_NOT_ACCEPTABLE -> NotAcceptable(throwable)                 // 406
                        HttpURLConnection.HTTP_GATEWAY_TIMEOUT,                                           // 504
                        HttpURLConnection.HTTP_CLIENT_TIMEOUT -> TimeOut(throwable)                       // 408
                        HttpURLConnection.HTTP_CONFLICT -> Conflict(throwable)                            // 409
                        HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> UnsupportedType(throwable)             // 415
                        TOO_MANY_REQUESTS -> TooManyRequests(throwable)                                   // 429
                        UNKNOWN_SESSION -> UnknownSession(throwable)                                      // 429
                        HttpURLConnection.HTTP_BAD_GATEWAY,                                               // 502
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> ServerError(throwable)                   // 500

                        else -> Generic(throwable)
                    }
                }

                is SQLiteException -> DatabaseOperationException(throwable)

                else -> {
                    Generic(throwable)
                }
            }
    }
}