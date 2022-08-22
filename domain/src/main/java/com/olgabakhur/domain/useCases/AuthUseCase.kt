package com.olgabakhur.domain.useCases

import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.repository.UserPreferencesRepository
import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onError
import com.olgabakhur.data.util.result.onSuccess
import com.olgabakhur.domain.util.validation.isEmailValid
import com.olgabakhur.domain.util.validation.isPasswordValid
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    fun getIsUserLoggedIn(): Flow<Boolean> = userPreferencesRepository.getIsUserLoggedIn()

    suspend fun setIsUserLoggedIn(isLoggedIn: Boolean) =
        userPreferencesRepository.setIsUserLoggedIn(isLoggedIn)

    /* Assume fields are not empty*/
    suspend fun signIn(email: String, password: String): Result<UserCredentials> {
        if (!isEmailValid(email)) {
            return Result.Error(ApplicationError.InvalidEmail)
        }

        if (!isPasswordValid(password)) {
            return Result.Error(ApplicationError.InvalidPassword)
        }

        val result = newsRepository.signIn(email, password)

        return result
            .onSuccess {
                setIsUserLoggedIn(true)
                return result
            }
            .onError { appError ->
                setIsUserLoggedIn(false)

                val loginError = when (appError) {
                    is ApplicationError.Unauthorized -> ApplicationError.UserIsNotRegistered
                    /* Handle all the possible cases */
                    else -> appError
                }

                return Result.Error(loginError)
            }
    }
}