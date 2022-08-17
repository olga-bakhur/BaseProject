package com.olgabakhur.domain.useCases

import com.olgabakhur.data.model.news.auth.AuthRequest
import com.olgabakhur.data.model.news.auth.AuthResponse
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onError
import com.olgabakhur.data.util.result.onSuccess
import com.olgabakhur.domain.util.validation.isEmailValid
import com.olgabakhur.domain.util.validation.isPasswordValid
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    /* Assume fields are not empty*/
    suspend fun signIn(email: String, password: String): Result<AuthResponse> {
        if (!isEmailValid(email)) {
            return Result.Error(ApplicationError.InvalidEmail)
        }

        if (!isPasswordValid(password)) {
            return Result.Error(ApplicationError.InvalidPassword)
        }

        val result = newsRepository.signIn(
            AuthRequest(
                email = email,
                password = password
            )
        )

        return result
            .onSuccess { return result }
            .onError { appError ->
                val loginError = when (appError) {
                    is ApplicationError.Unauthorized -> ApplicationError.UserIsNotRegistered
                    /* Handle all the possible cases */
                    else -> appError
                }

                return Result.Error(loginError)
            }
    }
}