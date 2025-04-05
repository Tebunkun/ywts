package com.example.ywts22b1num7184.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class SettingsPreference(private val context: Context) {
    companion object {
        val DISPLAY_MODE_KEY = stringPreferencesKey("display_mode")
    }

    val displayModeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[DISPLAY_MODE_KEY] ?: "both"
    }

    suspend fun saveDisplayMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[DISPLAY_MODE_KEY] = mode
        }
    }
}
