package com.olgabakhur.data.util

import com.google.gson.Gson
import com.olgabakhur.data.util.error.ApplicationError
import retrofit2.HttpException

fun HttpException.getCustomHttpError(): Int? {
    return try {
        this.response()?.errorBody()?.charStream()?.let {
            Gson().fromJson(it, com.olgabakhur.data.model.error.ErrorResponse::class.java).error
        }
    } catch (exception: Exception) {
        null
    }
}

fun Int?.getApplicationErrorOrNull() = when (this) {
    1 -> ApplicationError.EmptyFiled
    else -> null
}