package com.olgabakhur.domain.util

import com.google.gson.Gson
import com.olgabakhur.data.model.error.ErrorResponse
import retrofit2.HttpException

fun HttpException.getCustomHttpError(): Int? {
    return try {
        this.response()?.errorBody()?.charStream()?.let {
            Gson().fromJson(it, ErrorResponse::class.java).error
        }
    } catch (exception: Exception) {
        null
    }
}

fun Int?.getApplicationErrorOrNull() = when (this) {
    1 -> com.olgabakhur.domain.util.error.ApplicationError.EmptyFiled
    else -> null
}