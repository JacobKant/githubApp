package ru.jacobkant.githubapp.data.preferences

import android.content.Context
import javax.inject.Inject

interface DefaultPreferences {
    var vkToken: String
}

class DefaultPreferencesImpl @Inject constructor(context: Context) : DefaultPreferences {
    private val prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)

    override var vkToken: String by PreferencesDelegate(prefs, "vkToken", "")

}

