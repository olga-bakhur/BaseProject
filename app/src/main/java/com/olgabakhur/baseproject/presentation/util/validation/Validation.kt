package com.olgabakhur.baseproject.presentation.util.validation

import com.olgabakhur.domain.util.result.Result


//fun Result<String>.checkEmpty(): Result<String> {
//    return if (this is Result.Success) {
//        if (value.isNotEmpty()) Result.Success(value)
//        else Result.Error(ApplicationError.EmptyFiled)
//    } else this
//}

private fun String.matchTo(regExp: String) = matches(regExp.toRegex())

private fun String.isEmailValid() = this.matchTo(EMAIL)