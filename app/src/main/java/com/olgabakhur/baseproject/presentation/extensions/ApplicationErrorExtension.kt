package com.olgabakhur.baseproject.presentation.extensions

import android.content.Context
import com.olgabakhur.baseproject.R
import com.olgabakhur.data.util.error.ApplicationError

fun ApplicationError.message(context: Context): String =
    if (this is ApplicationError.Generic) {

        if (this.throwableMessage != null) {
            this.throwableMessage!!
        } else {
            context.resources.getString(R.string.error_generic)
        }
    } else {
        context.resources.getString(
            when (this) {
                // Common errors
                is ApplicationError.NoInternetConnection -> R.string.error_no_internet_connection

                // Http errors
                is ApplicationError.BadRequest -> R.string.error_bad_request
                is ApplicationError.Unauthorized -> R.string.error_unauthorized
                is ApplicationError.Forbidden -> R.string.error_forbidden
                is ApplicationError.NotFound -> R.string.error_not_found
                is ApplicationError.TimeOut -> R.string.error_timeout
                is ApplicationError.Conflict -> R.string.error_conflict
                is ApplicationError.UnsupportedType -> R.string.error_unsupported_type
                is ApplicationError.TooManyRequests -> R.string.error_too_many_requests
                is ApplicationError.Server -> R.string.error_server

                // Database operation error
                is ApplicationError.DatabaseOperation -> R.string.error_database_operation

                // Login errors
                is ApplicationError.UserIsNotRegistered -> R.string.error_user_is_not_registered
                is ApplicationError.UserIsAlreadyRegistered -> R.string.error_user_is_already_registered
                else -> R.string.error_generic
            }
        )
    }

