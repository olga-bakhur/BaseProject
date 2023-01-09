package com.olgabakhur.baseproject.presentation.util.stringresourcehelper

import android.content.Context
import com.olgabakhur.baseproject.R
import com.olgabakhur.data.util.error.ApplicationError

fun ApplicationError.message(context: Context): String =
    context.resources.getString(
        when (this) {
            // Generic errors
            is ApplicationError.Generic -> R.string.error_generic

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

            // Database operation error
            is ApplicationError.DatabaseOperationException -> R.string.error_database_operation
        }
    )

