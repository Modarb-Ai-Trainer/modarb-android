package com.modarb.android.ui.onboarding.utils.UserPref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.modarb.android.ui.onboarding.models.data
import com.modarb.android.ui.onboarding.utils.Data

object UserPrefUtil {
    private const val PREF_FILE_NAME = "myPreferences"
    private const val DATA_KEY = "data_key"
    private const val LOGGED_IN_KEY = "logged_in_status"
    private val gson = Gson()

    fun saveUserData(context: Context, data: data) {
        val jsonData = gson.toJson(data)
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(DATA_KEY, jsonData)
            .apply()
    }

    fun getUserData(context: Context): Data? {
        val jsonData = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .getString(DATA_KEY, null) ?: return null
        val type = object : TypeToken<Data>() {}.type
        return gson.fromJson(jsonData, type)
    }

    fun setUserLoggedIn(context: Context, isLoggedIn: Boolean) {
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(LOGGED_IN_KEY, isLoggedIn)
            .apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .getBoolean(LOGGED_IN_KEY, false)
    }
}
