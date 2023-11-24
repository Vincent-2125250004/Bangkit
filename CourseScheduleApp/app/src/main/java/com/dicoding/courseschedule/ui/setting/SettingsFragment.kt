package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var dailyReminder: DailyReminder

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is String) {
                val selectedNightMode = NightMode.valueOf(newValue.uppercase(Locale.US)).value
                updateTheme(selectedNightMode)
            }
            true
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        val dailyReminderSwitch =
            findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        dailyReminderSwitch?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is Boolean) {
                if (newValue) {
                    dailyReminder.setDailyReminder(requireContext())
                    true
                } else {
                    dailyReminder.cancelAlarm(requireContext())
                }
                true
            } else {
                false
            }
        }

        dailyReminder = DailyReminder()
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}