package com.olgabakhur.data.repositoryimpl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object NetworkConnectivityManager {

    private val _connectivityFlow = MutableStateFlow(false)
    val connectivityFlow = _connectivityFlow.asStateFlow()

    fun initConnectivityListener(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _connectivityFlow.value = true
            }

            override fun onLost(network: Network) {
                _connectivityFlow.value = false
            }
        }

        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}