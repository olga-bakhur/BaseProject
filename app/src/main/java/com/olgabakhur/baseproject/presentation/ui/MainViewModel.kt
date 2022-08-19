package com.olgabakhur.baseproject.presentation.ui

import androidx.lifecycle.viewModelScope
import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.domain.interactors.AuthInteractor
import com.olgabakhur.domain.interactors.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val authInteractor: AuthInteractor
) : BaseViewModel() {

    fun getApplicationErrors() = mainInteractor.getApplicationErrors()

    /* Fake Sign out implementation. Replace with the real one. */
    fun signOutFake(navigateToStartDestination: () -> Unit) {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                authInteractor.setIsUserLoggedIn(false)
            }.join()

            withContext(Dispatchers.Main) {
                navigateToStartDestination()
            }
        }
    }
}