package com.dicoding.todoapp.setting

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dicoding.todoapp.R
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    private lateinit var periodicWorkRequest: PeriodicWorkRequest

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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

            val prefNotification =
                findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { _, newValue ->
                val channelName = getString(R.string.notify_channel_name)
                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName

                if (newValue as Boolean) {
                    val data =
                        Data.Builder().putString(NOTIFICATION_CHANNEL_ID, channelName).build()
                    val periodicWorkRequest = PeriodicWorkRequest.Builder(
                        NotificationWorker::class.java,
                        24, TimeUnit.HOURS
                    ).setInputData(data).build()

                    WorkManager.getInstance(mContext).enqueue(periodicWorkRequest)
                    Toast.makeText(context, "Repeating Alarm is on", Toast.LENGTH_SHORT).show()

                } else {
                    WorkManager.getInstance(mContext).cancelAllWorkByTag(channelName)
                    Toast.makeText(context, "Repeating Alarm is off", Toast.LENGTH_SHORT).show()
                }
                true
            }

        }
    }
}