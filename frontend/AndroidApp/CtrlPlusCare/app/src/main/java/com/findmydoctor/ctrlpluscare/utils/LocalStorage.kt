package com.findmydoctor.ctrlpluscare.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Extension property on Context
private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class LocalStorage(private val context: Context) {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        val FCM_TOKEN_KEY = stringPreferencesKey("fcm_token")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    suspend fun saveFcmToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[FCM_TOKEN_KEY] = token
        }}
    suspend fun getFcmToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[FCM_TOKEN_KEY]
    }


    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    // Get token
    suspend fun getToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    // Save login state
    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit {
            it[IS_LOGGED_IN] = isLoggedIn
        }
    }

    // Read login state
    suspend fun isLoggedIn(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[IS_LOGGED_IN] ?: false
    }

    // Clear all data (Logout)
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}