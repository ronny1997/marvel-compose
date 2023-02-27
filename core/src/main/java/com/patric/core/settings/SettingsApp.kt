package com.patric.core.settings

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

interface SettingsApp {

    companion object {
        val TOKEN = stringPreferencesKey(Key.TOKEN.name)
        val VERSION = stringPreferencesKey(Key.VERSION.name)
        val MENU = stringPreferencesKey(Key.MENU.name)
        val DEVICE_CODE = stringPreferencesKey(Key.DEVICE_CODE.name)
        val BASE_URL = stringPreferencesKey(Key.BASE_URL.name)
        val TOKEN_EXPIRATION_DATE = stringPreferencesKey(Key.TOKEN_EXPIRATION_DATE.name)
        val LANGUAGE_PREFERENCE = stringPreferencesKey(Key.LANGUAGE_PREFERENCE.name)
        val LITERALS = stringPreferencesKey(Key.LITERALS.name)

    }

    suspend fun <T> get(key: Preferences.Key<T>): T?
    suspend fun <T> set(key: Preferences.Key<T>, value: T)
    suspend fun clearData()

    suspend fun setLiterals(plainLiterals: String)
    suspend fun getLiteral(id: String): String

    private enum class Key {
        TOKEN,
        VERSION,
        MENU,
        DEVICE_CODE,
        BASE_URL,
        TOKEN_EXPIRATION_DATE,
        LANGUAGE_PREFERENCE,
        LITERALS;
    }
}