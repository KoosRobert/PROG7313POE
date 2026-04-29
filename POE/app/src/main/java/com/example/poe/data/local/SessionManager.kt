package com.example.poe.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "budget_session",
            Context.MODE_PRIVATE
        )

    fun saveLogin(username: String) {

        prefs.edit()
            .putString("username", username)
            .apply()
    }

    fun isLoggedIn(): Boolean {

        return prefs.getString(
            "username",
            null
        ) != null
    }

    fun logout() {

        prefs.edit().clear().apply()
    }
}