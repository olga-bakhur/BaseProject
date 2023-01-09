package com.olgabakhur.baseproject.presentation.util.stringresourcehelper

import android.content.Context
import com.olgabakhur.baseproject.R
import com.olgabakhur.data.repository.NetworkConnectivityObserver

object NetworkConnectivityHelper {

    fun getLocalizedName(status: NetworkConnectivityObserver.Status, context: Context): String =
        context.resources.getString(
            when (status) {
                NetworkConnectivityObserver.Status.Available -> R.string.network_connectivity_status_available
                NetworkConnectivityObserver.Status.Unavailable -> R.string.network_connectivity_status_unavailable
                NetworkConnectivityObserver.Status.Losing -> R.string.network_connectivity_status_losing
                NetworkConnectivityObserver.Status.Lost -> R.string.network_connectivity_status_lost
            }
        )
}