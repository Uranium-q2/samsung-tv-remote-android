package com.example.samsungtvremote.data

import android.content.Context
import androidx.core.content.edit

/** Remembers the TV's IP address, its name and the auth token. */
class TvPrefs(context: Context) {
    private val prefs = context.getSharedPreferences("tv", Context.MODE_PRIVATE)

    var ip: String
        get() = prefs.getString("ip", "") ?: ""
        set(value) = prefs.edit { putString("ip", value) }

    var name: String
        get() = prefs.getString("name", "") ?: ""
        set(value) = prefs.edit { putString("name", value) }

    var token: String?
        get() = prefs.getString("token", null)
        set(value) = prefs.edit { putString("token", value) }

    /** Forgets the saved TV completely (the next connection asks for approval again). */
    fun clear() = prefs.edit { clear() }
}
