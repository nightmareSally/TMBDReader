package com.sally.tmbdreader.util

import android.content.SharedPreferences

class SharedPreference(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val ACCOUNT_ID = "ACCOUNT_ID"
        private const val SESSION_ID = "SESSION_ID"
    }

    var accountId: Int?
        get() = sharedPreferences.getInt(ACCOUNT_ID, -1)
        set(value) = setValue(ACCOUNT_ID, value)

    var sessionId: String?
        get() = sharedPreferences.getString(SESSION_ID, null)
        set(value) = setValue(SESSION_ID, value)

    fun <T> setValue(key: String, value: T) {
        val editor = sharedPreferences.edit()
        when (value) {
            null -> editor.remove(key)
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
        }
        editor.apply()
    }

}