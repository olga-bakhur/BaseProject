package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.useCases.AuthUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authUseCase: AuthUseCase
) {

    fun getIsUserLoggedIn(): Flow<Boolean> = authUseCase.getIsUserLoggedIn()

    suspend fun setIsUserLoggedIn(isLoggedIn: Boolean) = authUseCase.setIsUserLoggedIn(isLoggedIn)

    suspend fun signIn(email: String, password: String): Result<UserCredentials> =
        authUseCase.signIn(email, password)
}