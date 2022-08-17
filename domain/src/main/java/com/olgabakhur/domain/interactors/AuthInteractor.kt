package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.news.auth.AuthResponse
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.useCases.AuthUseCase
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authUseCase: AuthUseCase
) {

    suspend fun signIn(email: String, password: String): Result<AuthResponse> =
        authUseCase.signIn(email, password)
}