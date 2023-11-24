package com.dicoding.habitapp.setting

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.dicoding.habitapp.R
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.DarkMode
import com.dicoding.habitapp.utils.NOTIFICATION_CHANNEL_ID
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Notifications permission granted")
            } else {
                showToast("Notifications will not show without permission")
            }
        }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT > 32) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var mContext: Context

        override fun onAttach(context: Context) {
            super.onAttach(context)
            mContext = context
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            themePreference?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    val selectedTheme = DarkMode.valueOf(newValue.uppercase(Locale.US)).value
                    updateTheme(selectedTheme)
                }
                true
            }

            val notificationSwitch =
                findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            notificationSwitch?.setOnPreferenceChangeListener { _, newValue ->
                val channelName = getString(R.string.notify_channel_name)

                if (newValue as Boolean) {
                    val data =
                        Data.Builder().putString(NOTIFICATION_CHANNEL_ID, channelName).build()
                    val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
                        NotificationWorker::class.java,
                    ).setInputData(data).build()

                    WorkManager.getInstance(mContext).enqueue(oneTimeWorkRequest)


                } else {
                    WorkManager.getInstance(mContext).cancelAllWorkByTag(channelName)
                }
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}