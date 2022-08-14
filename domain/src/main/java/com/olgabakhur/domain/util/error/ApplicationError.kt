package com.olgabakhur.domain.util.error

sealed class ApplicationError {

    companion object {
        const val TOO_MANY_REQUESTS = 429
    }

    // Common errors
    object Generic : ApplicationError()
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
}