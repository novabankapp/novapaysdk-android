package com.novapay.sdk.infrastructure.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preferences  constructor(
    val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
    val accessToken: Flow<String?> = context.dataStore.data.map {  preferences ->
            preferences[KEY_AUTH_TOKEN]
        }

    suspend fun setAuthToken(token: String) {
        context.dataStore.edit { settings ->
            settings[KEY_AUTH_TOKEN] = token
        }
    }


    private val IS_VERIFIED = booleanPreferencesKey("verified")
    val isVerified: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[IS_VERIFIED]
        }

    suspend fun setIsVerified(isVerified : Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_VERIFIED] = isVerified
        }
    }

    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val refreshToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_REFRESH_TOKEN]
        }

    suspend fun setRefreshToken(token: String) {
        context.dataStore.edit { settings ->
            settings[KEY_REFRESH_TOKEN] = token
        }
    }

    private val ENCRYPTION_KEY = stringPreferencesKey("encryptionKey")
    val encryptionKey: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ENCRYPTION_KEY]
        }

    suspend fun setEncryptionKey(key: String) {
        context.dataStore.edit { settings ->
            settings[ENCRYPTION_KEY] = key
        }
    }

    private val SOCIAL_MEDIA_LOGIN = booleanPreferencesKey("social_login")
    val socialLogin: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SOCIAL_MEDIA_LOGIN] ?: false
        }

    suspend fun setSocialLogin(loggedIn: Boolean) {
        context.dataStore.edit { settings ->
            settings[SOCIAL_MEDIA_LOGIN] = loggedIn
        }
    }

    val DARK_MODE = booleanPreferencesKey("isDarkMode")
    val isDarkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[DARK_MODE] ?: true
        }
    suspend fun switchDarkMode(isDarkMode : Boolean) {

        context.dataStore.edit { settings ->

            settings[DARK_MODE] = isDarkMode
        }
    }

    companion object {
        const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
    }
}