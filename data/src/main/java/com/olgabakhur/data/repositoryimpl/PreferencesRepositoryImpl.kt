package com.olgabakhur.data.repositoryimpl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.olgabakhur.data.repository.PreferencesRepository
import com.olgabakhur.data.repositoryimpl.PreferencesRepositoryImpl.PreferencesKeys.SAMPLE_INT_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    companion object {
        private const val TAG = "DataStore"
        const val DEFAULT_INT_VALUE: Int = 1
    }

    private object PreferencesKeys {
        val SAMPLE_INT_VALUE = intPreferencesKey("SAMPLE_INT_VALUE")
    }

    override fun getIntSample(): Flow<Int> = getValue(SAMPLE_INT_VALUE, DEFAULT_INT_VALUE)

    override suspend fun setIntSample(value: Int) = setValue(SAMPLE_INT_VALUE, value)

    override suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStore.data
            .catch { throwable ->
                if (throwable is IOException) {
                    emit(emptyPreferences())
                } else {
                    Log.e(
                        TAG,
                        "Error occurred during working with the DataStore: ${throwable.message}"
                    )
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }.distinctUntilChanged()

    private suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}