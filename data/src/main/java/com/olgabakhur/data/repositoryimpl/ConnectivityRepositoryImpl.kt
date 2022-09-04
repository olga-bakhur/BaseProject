package com.olgabakhur.data.repositoryimpl

import com.katalog.data.connectivity.ServerWebSocketListener
import com.olgabakhur.data.BuildConfig
import com.olgabakhur.data.repository.ConnectivityRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class ConnectivityRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : ConnectivityRepository {

    override fun initWebSocketConnection() {
        val request = Request.Builder()
            .url(BuildConfig.BASE_URL_WEB_SOCKET)
            .build()

        okHttpClient.newWebSocket(request, ServerWebSocketListener())
    }

    override fun closeWebSocketConnection() {
        // TODO
    }
}