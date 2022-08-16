package com.olgabakhur.baseproject.presentation.extensions

import android.content.Context
import com.olgabakhur.baseproject.R
import com.olgabakhur.data.util.error.ApplicationError

fun ApplicationError.message(context: Context): String =
    context.resources.getString(
        when (this) {
            // Common errors
            ApplicationError.Unknown -> R.string.error_generic
            ApplicationError.NoInternetConnection -> R.string.error_no_internet_connection

            // Http errors
            ApplicationError.BadRequest -> R.string.error_bad_request
            ApplicationError.Unauthorized -> R.string.error_unauthorized
            ApplicationError.Forbidden -> R.string.error_forbidden
            ApplicationError.NotFound -> R.string.error_not_found
            ApplicationError.TimeOut -> R.string.error_timeout
            ApplicationError.Conflict -> R.string.error_conflict
            ApplicationError.UnsupportedType -> R.string.error_unsupported_type
            ApplicationError.TooManyRequests -> R.string.error_too_many_requests
            ApplicationError.Server -> R.string.error_server
            ApplicationError.Server -> R.string.error_server

            // Database operation error
            ApplicationError.DatabaseOperation -> R.string.error_database_operation
        }
    )

