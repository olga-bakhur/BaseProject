package com.olgabakhur.data.util.error

sealed class ApplicationError() {

    companion object {
        const val TOO_MANY_REQUESTS = 429
    }

    // Common errors
    class Generic(val throwableMessage: String? = null) : ApplicationError()
    object NoInternetConnection : ApplicationError()

    // Http errors
    object BadRequest : ApplicationError()
    object Unauthorized : ApplicationError()
    object Forbidden : ApplicationError()
    object NotFound : ApplicationError()
    object TimeOut : ApplicationError()
    object Conflict : ApplicationError()
    object UnsupportedType : ApplicationError()
    object TooManyRequests : ApplicationError()
    object Server : ApplicationError()

    // Database operation error
    object DatabaseOperation : ApplicationError()

    // Login errors
    object UserIsNotRegistered : ApplicationError()
    object UserIsAlreadyRegistered : ApplicationError()

    fun isGenericError(): Boolean =
        when (this) {
            is Generic,
            is NoInternetConnection -> true
            else -> false
        }
}