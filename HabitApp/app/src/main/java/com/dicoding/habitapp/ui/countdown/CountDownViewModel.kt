package com.dicoding.habitapp.ui.countdown

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CountDownViewModel : ViewModel() {

    private var timer: CountDownTimer? = null

    private val initialTime = MutableLiveData<Long>()
    val currentTime = MutableLiveData<Long>()

    // The String version of the current time (hh:mm:ss)
    val currentTimeString = currentTime.map { time ->
        DateUtils.formatElapsedTime(time / 1000)
    }

    // Event which triggers the end of count down
    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish

    fun setInitialTime(minuteFocus: Long) {
        val initialTimeMillis = minuteFocus * 60 * 1000
        initialTime.value = initialTimeMillis
        currentTime.value = initialTimeMillis

        timer = object : CountDownTimer(initialTimeMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
            }

            override fun onFinish() {
                resetTimer()
            }
        }
    }


    fun startTimer(habitId: Int, habitTitle: String) {
        viewModelScope.launch {
            timer?.start()
            scheduleReminderWork(habitId, habitTitle)
        }
    }

    fun resetTimer() {
        viewModelScope.launch {
            timer?.cancel()
            currentTime.value = initialTime.value
            _eventCountDownFinish.value = true
            cancelReminderWork()
        }

    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun scheduleReminderWork(habitId: Int, habitTitle: String) {
        val data = workDataOf(
            HABIT_ID to habitId,
            HABIT_TITLE to habitTitle
        )

        val reminderWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(data)
            .setInitialDelay(initialTime.value ?: 0, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance().enqueue(reminderWork)
    }

    private fun cancelReminderWork() {
        WorkManager.getInstance().cancelAllWorkByTag(NotificationWorker::class.java.simpleName)
    }

}