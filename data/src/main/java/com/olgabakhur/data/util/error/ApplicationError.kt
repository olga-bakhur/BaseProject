package com.olgabakhur.data.util.error

sealed class ApplicationError {

    companion object {
        const val TOO_MANY_REQUESTS = 429
        const val UNKNOWN_SESSION = 1001
    }

    // Generic errors
    class Generic(val throwable: Throwable? = null) : ApplicationError()

    // Network errors
    class NoInternetConnection(val throwable: Throwable) : ApplicationError()
    class SSLException(val throwable: Throwable) : ApplicationError()

    // Http errors
    class BadRequest(val throwable: Throwable) : ApplicationError()
    class Unauthorized(val throwable: Throwable) : ApplicationError()
    class Forbidden(val throwable: Throwable) : ApplicationError()
    class NotFound(val throwable: Throwable) : ApplicationError()
    class NotAcceptable(val throwable: Throwable) : ApplicationError()
    class TimeOut(val throwable: Throwable) : ApplicationError()
    class Conflict(val throwable: Throwable) : ApplicationError()
    class UnsupportedType(val throwable: Throwable) : ApplicationError()
    class TooManyRequests(val throwable: Throwable) : ApplicationError()
    class UnknownSession(val throwable: Throwable) : ApplicationError()
    class ServerError(val throwable: Throwable) : ApplicationError()

    // Database operation error
    class DatabaseOperationException(val throwable: Throwable) : ApplicationError()
}