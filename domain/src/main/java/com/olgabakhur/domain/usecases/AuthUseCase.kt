package com.olgabakhur.domain.usecases

import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.repository.ConnectivityRepository
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.repository.PreferencesRepository
import com.olgabakhur.data.util.error.ApplicationError
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.result.onError
import com.olgabakhur.data.util.result.onSuccess
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val connectivity: ConnectivityRepository,
    private val preferences: PreferencesRepository
) {

    suspend fun signIn(email: String, password: String): Result<UserCredentials> {
        val result = newsRepository.signIn(email, password)

        return result
            .onSuccess {
                doOnLogIn()
                return result
            }
            .onError { appError ->
                doOnLogOut()

                val loginError = when (appError) {
                    is ApplicationError.Unauthorized -> ApplicationError.UserIsNotRegistered
                    /* Handle all the possible cases */
                    else -> appError
                }

                return Result.Error(loginError)
            }
    }

    fun getIsUserLoggedIn(): Flow<Boolean> = preferences.getIsUserLoggedIn()

    suspend fun setIsUserLoggedIn(isLoggedIn: Boolean) =
        preferences.setIsUserLoggedIn(isLoggedIn)

    private suspend fun doOnLogIn() {
        preferences.setIsUserLoggedIn(true)
        // save user credentials
        // connectivity.initWebSocketConnection()
    }

    private suspend fun doOnLogOut() {
        preferences.setIsUserLoggedIn(false)
        // delete user credentials
        // connectivity.closeWebSocketConnection()
    }
}