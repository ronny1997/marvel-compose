package com.mpapps.marvelcompose.framework.datasources.local.cache.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {


    override suspend fun <T> get(key: String, clazz: Class<T>): T? {
        val preferenceKey = stringPreferencesKey(key)
        return dataStore.data.firstOrNull()?.let {
            Gson().fromJson(it[preferenceKey], clazz)
        }
    }

    override suspend fun <T> set(key: String, value: T, clazz: Class<T>) {
        val preferenceKey = stringPreferencesKey(key)
        dataStore.edit { prefs ->
            val json = Gson().toJson(value)
            prefs[preferenceKey] = json
        }
    }

    override fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it.clear()
            }
        }
    }

}