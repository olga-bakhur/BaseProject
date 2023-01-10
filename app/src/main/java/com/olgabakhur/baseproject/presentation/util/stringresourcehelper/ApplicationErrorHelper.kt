package com.olgabakhur.baseproject.presentation.util.stringresourcehelper

import android.content.Context
import com.olgabakhur.baseproject.R
import com.olgabakhur.data.util.error.ApplicationError

object ApplicationErrorHelper {

    fun getAppErrorDescription(appError: ApplicationError, context: Context): String =
        if (appError is ApplicationError.Generic) {
            appError.throwable.message ?: context.resources.getString(R.string.error_generic)
        } else {
            context.resources.getString(
                when (appError) {
                    // Network errors
                    is ApplicationError.NoInternetConnection -> R.string.error_no_internet_connection
                    is ApplicationError.SSLException -> R.string.error_ssl_exception

                    // Http errors
                    is ApplicationError.BadRequest -> R.string.error_bad_request
                    is ApplicationError.Unauthorized -> R.string.error_unauthorized
                    is ApplicationError.Forbidden -> R.string.error_forbidden
                    is ApplicationError.NotFound -> R.string.error_not_found
                    is ApplicationError.NotAcceptable -> R.string.error_not_acceptable
                    is ApplicationError.TimeOut -> R.string.error_timeout
                    is ApplicationError.Conflict -> R.string.error_conflict
                    is ApplicationError.UnsupportedType -> R.string.error_unsupported_type
                    is ApplicationError.TooManyRequests -> R.string.error_too_many_requests
                    is ApplicationError.UnknownSession -> R.string.error_unknown_session
                    is ApplicationError.ServerError -> R.string.error_server

                    // Database errors
                    is ApplicationError.DatabaseOperationException -> R.string.error_database_operation

                    else -> R.string.error_generic
                }
            )
        }
}

