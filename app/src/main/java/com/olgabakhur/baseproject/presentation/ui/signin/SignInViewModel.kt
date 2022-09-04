package com.olgabakhur.baseproject.presentation.ui.signin

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.interactors.AuthInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : BaseViewModel() {

    private val _signInFlow: MutableSharedFlow<Result<UserCredentials>> = MutableSharedFlow(1)
    val signInFlow: SharedFlow<Result<UserCredentials>> = _signInFlow.asSharedFlow()

    fun isUserLoggedIn() = authInteractor.getIsUserLoggedIn()

    /* Fake implementation */
    fun signInFake(navigateNext: () -> Unit) {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                authInteractor.setIsUserLoggedIn(true)
            }.join()

            withContext(Dispatchers.Main) {
                navigateNext()
            }
        }
    }

    /* Real implementation */
    fun signIn(email: String, password: String) =
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = authInteractor.signIn(email, password)
            _signInFlow.emit(result)
        }
}