package com.olgabakhur.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getIsUserLoggedIn(): Flow<Boolean>
    suspend fun setIsUserLoggedIn(isLoggedIn: Boolean)
}