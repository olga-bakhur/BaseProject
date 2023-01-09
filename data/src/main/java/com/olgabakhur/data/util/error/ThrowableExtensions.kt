package com.olgabakhur.data.util.error

import android.database.sqlite.SQLiteException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

fun Throwable.toApplicationError(): ApplicationError =
    when (this) {
        is SocketTimeoutException -> ApplicationError.TimeOut(this)
        is ConnectException,
        is UnknownHostException -> ApplicationError.NoInternetConnection(this)
        is SSLException -> ApplicationError.SSLException(this)
        is HttpException -> {
            when (this.code()) {
                HttpURLConnection.HTTP_BAD_REQUEST -> ApplicationError.BadRequest(this)                       // 400
                HttpURLConnection.HTTP_UNAUTHORIZED -> ApplicationError.Unauthorized(this)                    // 401
                HttpURLConnection.HTTP_FORBIDDEN -> ApplicationError.Forbidden(this)                          // 403
                HttpURLConnection.HTTP_NOT_FOUND -> ApplicationError.NotFound(this)                           // 404
                HttpURLConnection.HTTP_NOT_ACCEPTABLE -> ApplicationError.NotAcceptable(this)                 // 406
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT,                                                       // 504
                HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ApplicationError.TimeOut(this)                       // 408
                HttpURLConnection.HTTP_CONFLICT -> ApplicationError.Conflict(this)                            // 409
                HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> ApplicationError.UnsupportedType(this)             // 415
                ApplicationError.TOO_MANY_REQUESTS -> ApplicationError.TooManyRequests(this)                  // 429
                ApplicationError.UNKNOWN_SESSION -> ApplicationError.UnknownSession(this)                     // 429
                HttpURLConnection.HTTP_BAD_GATEWAY,                                                           // 502
                HttpURLConnection.HTTP_INTERNAL_ERROR -> ApplicationError.ServerError(this)                   // 500

                else -> ApplicationError.Generic(this)
            }
        }
        is SQLiteException -> ApplicationError.DatabaseOperationException(this)

        else -> {
            ApplicationError.Generic(this)
        }
    }