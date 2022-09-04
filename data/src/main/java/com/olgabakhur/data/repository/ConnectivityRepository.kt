package com.olgabakhur.data.repository

interface ConnectivityRepository {

    fun initWebSocketConnection()
    fun closeWebSocketConnection()
}