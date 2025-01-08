package pm.astraeus

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isEmpty
import com.google.android.material.bottomnavigation.BottomNavigationView
import pm.astraeus.databinding.ActivityMainBinding
import pm.astraeus.util.BaseActivity
import pm.astraeus.util.LocaleHelper
import java.util.Locale

class MainActivity : BaseActivity() {

    companion object {
        lateinit var navigation : BottomNavigationView
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navigation = binding.bottomNavigation

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navExplore -> {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        .replace(R.id.placeholder, ExploreFragment())
                        .commit()
                }

                R.id.navGlossary -> {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        .replace(R.id.placeholder, GlossaryFragment())
                        .commit()
                }
                R.id.navUser -> {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        .replace(R.id.placeholder, UserFragment())
                        .commit()
                }
            }
            return@setOnItemSelectedListener true
        }

        // Apply saved theme preference
        val sharedThemePreferences = getSharedPreferences("pmTheme", Context.MODE_PRIVATE)
        val isDarkMode = sharedThemePreferences.getBoolean("darkMode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.navExplore
        }

        // Load the user language from SharedPreferences
        val sharedLanguagePreferences = getSharedPreferences("pmLanguage", Context.MODE_PRIVATE)
        val savedLanguage = sharedLanguagePreferences.getString("language", "en") ?: "en"
        LocaleHelper.setAppLocale(this, savedLanguage)
    }

    override fun onStart() {
        super.onStart()

        if (binding.placeholder.isEmpty()) {
            binding.bottomNavigation.selectedItemId = R.id.navExplore
        }
    }

}