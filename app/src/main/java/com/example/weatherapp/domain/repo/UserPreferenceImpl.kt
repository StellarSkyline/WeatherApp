package com.example.weatherapp.domain.repo

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.di.interfaces.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(val context: Context): UserPreferences {
    private val Context.dataStore by preferencesDataStore("user_preferences")

    init{
        Log.d("STLog", "Initalized UserPref")
    }

    override suspend fun saveItem(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getItem(key: String): String? {
        return context.dataStore.data.map {
            it[stringPreferencesKey(key)]
        }.first()
    }
}