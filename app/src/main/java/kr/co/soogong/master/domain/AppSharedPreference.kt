package kr.co.soogong.master.domain

import android.content.Context
import android.content.Context.MODE_PRIVATE

class AppSharedPreference internal constructor(
    private val context: Context
) {
    fun setBoolean(key: String, value: Boolean) {
        val pref = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean) =
        context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
            .getBoolean(key, defaultValue)

    fun setInteger(key: String, value: Int) {
        val pref = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInteger(key: String, defaultValue: Int) =
        context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
            .getInt(key, defaultValue)

    fun setFloat(key: String, value: Float) {
        val pref = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String, defaultValue: Float) =
        context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
            .getFloat(key, defaultValue)

    fun setString(key: String, value: String) {
        val pref = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String?) =
        context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
            .getString(key, defaultValue)

    companion object {
        private const val PREFERENCES_NAME = "soogong_shared_preferences"
    }
}