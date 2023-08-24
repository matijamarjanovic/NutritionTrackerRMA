import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        private const val PREF_NAME = "MyAppPreferences"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"

        // Singleton instance
        private var instance: SharedPreferencesManager? = null

        // Function to get or create the Singleton instance
        fun getInstance(): SharedPreferencesManager {
            if (instance == null) {
                instance = SharedPreferencesManager()
            }
            return instance!!
        }
    }

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun saveUsername(username: String) {
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    fun clearLoggedIn() {
        editor.clear()
        editor.apply()
    }
}
