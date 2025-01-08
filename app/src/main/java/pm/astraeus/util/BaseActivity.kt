package pm.astraeus.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import pm.astraeus.util.LocaleHelper

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("pmLanguage", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("language", "en") ?: "en"

        // Apply the saved locale to the new base context
        val context = LocaleHelper.setAppLocale(newBase, savedLanguage)
        super.attachBaseContext(context)
    }
}
