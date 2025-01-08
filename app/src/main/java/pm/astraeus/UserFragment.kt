package pm.astraeus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import pm.astraeus.util.LocaleHelper

class UserFragment : Fragment() {

    private lateinit var languageSpinner: Spinner
    private lateinit var themeSwitch: SwitchMaterial
    private val languages = listOf("English", "Português")
    private var isRecreating = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        // Logout button
        val logoutButton: Button = view.findViewById(R.id.btnLogout)
        logoutButton.setOnClickListener { doLogout() }

        // Theme toggle switch
        themeSwitch = view.findViewById(R.id.switchTheme)
        val sharedPreferences = requireActivity().getSharedPreferences("pmTheme", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("darkMode", false)

        // Set initial state of the switch
        themeSwitch.isChecked = isDarkMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            toggleTheme(isChecked)
        }

        // Language spinner setup
        languageSpinner = view.findViewById(R.id.languageSpinner)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            languages
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        // Set the spinner to the saved language
        val currentLanguage = getUserLanguage()
        val languagePosition = if (currentLanguage == "en") 0 else 1
        languageSpinner.setSelection(languagePosition)

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = if (position == 0) "en" else "pt"

                if (getUserLanguage() != selectedLanguage) {
                    saveUserLanguage(selectedLanguage)
                    LocaleHelper.setAppLocale(requireContext(), selectedLanguage)

                    // Recreate the activity to apply the language change
                    if (!isRecreating) {
                        isRecreating = true
                        requireActivity().recreate()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        return view
    }

    private fun doLogout() {
        requireActivity().getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun toggleTheme(isDarkMode: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences("pmTheme", Context.MODE_PRIVATE)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        sharedPreferences.edit().putBoolean("darkMode", isDarkMode).apply()

        if (!isRecreating) {
            isRecreating = true
            requireActivity().recreate()
        }
    }

    private fun getUserLanguage(): String {
        val sharedPreferences = requireContext().getSharedPreferences("pmLanguage", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    private fun saveUserLanguage(languageCode: String) {
        val sharedPreferences = requireContext().getSharedPreferences("pmLanguage", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", languageCode).apply()
    }
}