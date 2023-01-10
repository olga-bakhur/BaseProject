package com.olgabakhur.baseproject.presentation.ui

import com.olgabakhur.baseproject.presentation.base.BaseViewModel
import com.olgabakhur.data.repository.NetworkConnectivityObserver
import com.olgabakhur.domain.interactors.MainInteractor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainInteractor: MainInteractor,
    networkConnectivityObserver: NetworkConnectivityObserver
) : BaseViewModel() {

    val flowNetworkConnectivityStatus = networkConnectivityObserver.observe()

    fun getFlowApplicationErrors() = mainInteractor.getApplicationErrors()
}