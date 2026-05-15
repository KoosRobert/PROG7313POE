package com.example.poe.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            "budgetbuddy_session",
            Context.MODE_PRIVATE
        )

    // ---------------- SAVE LOGIN ----------------

    fun saveLogin(username: String) {

        sharedPreferences.edit()
            .putBoolean("is_logged_in", true)
            .putString("username", username)
            .apply()
    }

    // ---------------- CHECK LOGIN ----------------

    fun isLoggedIn(): Boolean {

        return sharedPreferences.getBoolean(
            "is_logged_in",
            false
        )
    }

    // ---------------- GET LOGGED IN USER ----------------

    fun getLoggedInUser(): String? {

        return sharedPreferences.getString(
            "username",
            null
        )
    }

    // ---------------- LOGOUT ----------------

    fun logout() {

        sharedPreferences.edit()
            .clear()
            .apply()
    }
}