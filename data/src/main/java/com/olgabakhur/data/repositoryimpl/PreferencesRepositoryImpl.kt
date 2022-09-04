package com.olgabakhur.data.repositoryimpl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.olgabakhur.data.repository.PreferencesRepository
import com.olgabakhur.data.repositoryimpl.PreferencesRepositoryImpl.PreferencesKeys.IS_USER_LOGGED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    companion object {
        private const val TAG = "DataStore"
    }

    private object PreferencesKeys {
        val IS_USER_LOGGED_IN = booleanPreferencesKey("IS_USER_LOGGED_IN")
    }

    override fun getIsUserLoggedIn(): Flow<Boolean> =
        dataStore.data
            .catch { throwable ->
                if (throwable is IOException) {
                    emit(emptyPreferences())
                } else {
                    Log.e(
                        TAG,
                        "Error occurred during working with the DataStore: ${throwable.message}"
                    )
                    throw throwable // TODO: handle exception
                }
            }
            .map { preferences ->
                preferences[IS_USER_LOGGED_IN] ?: false
            }


    override suspend fun setIsUserLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isLoggedIn
        }
    }
}