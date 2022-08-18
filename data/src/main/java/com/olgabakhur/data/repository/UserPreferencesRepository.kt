package com.olgabakhur.data.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getIsUserLoggedIn(): Flow<Boolean>
    suspend fun setIsUserLoggedIn(isLoggedIn: Boolean)
}