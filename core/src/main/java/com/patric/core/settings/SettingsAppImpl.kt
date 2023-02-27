package com.patric.core.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.common.base.CaseFormat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patric.core.settings.SettingsApp.Companion.LITERALS
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class SettingsAppImpl(
    val dataStore: DataStore<Preferences>
) : SettingsApp {

    private val literals = mutableMapOf<String, String>()
    private val mapType = object : TypeToken<Map<String, String>>() {}.type

    override suspend fun <T> get(key: Preferences.Key<T>): T? {
        return dataStore.data.map { preferences ->
            preferences[key]
        }.first()
    }

    override suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[key] = value
        }
    }

    override suspend fun clearData() {
        dataStore.edit { it.clear() }
    }

    override suspend fun setLiterals(plainLiterals: String) {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        val responseMap: Map<String, String> = Gson().fromJson(plainLiterals, mapType)
        responseMap.forEach {
            literals[it.key.replace("login.", "").replace("device.", "")] = it.value
        }
        set(LITERALS, Gson().toJson(literals))
    }

    override suspend fun getLiteral(id: String): String = when {
        literals.isNotEmpty() -> literals[CaseFormat.UPPER_UNDERSCORE.to(
            CaseFormat.LOWER_CAMEL,
            id
        )] ?: "Texto no encontrado"
        get(LITERALS)?.isNotEmpty() == true -> (Gson().fromJson(
            get(LITERALS),
            mapType
        ) as Map<String, String>?)?.get(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, id))
            ?: "Texto no encontrado"
        else -> "Texto no encontrado"
    }
}