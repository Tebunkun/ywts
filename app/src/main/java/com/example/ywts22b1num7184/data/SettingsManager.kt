package com.example.ywts22b1num7184.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManager(private val context: Context) {
    companion object {
        val VISIBILITY_KEY = stringPreferencesKey("visibility_mode")
        const val SHOW_BOTH = "Хоёуланг нь ил харуулна"
        const val SHOW_FOREIGN = "Гадаад үгнийг ил харуулна"
        const val SHOW_NATIVE = "Монгол үгнийг ил харуулна"
    }

    val visibilityMode: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[VISIBILITY_KEY] ?: SHOW_BOTH
    }

    suspend fun setVisibilityMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[VISIBILITY_KEY] = mode
        }
    }
}
