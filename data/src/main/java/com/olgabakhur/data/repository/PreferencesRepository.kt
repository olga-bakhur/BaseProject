package com.olgabakhur.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getIntSample(): Flow<Int>
    suspend fun setIntSample(value: Int)
    suspend fun clearDataStore()
}