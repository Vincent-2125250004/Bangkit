package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.utils.HABIT

class CountDownActivity : AppCompatActivity() {

    private var currentTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"

        val habit = intent.getParcelableExtra<Habit>(HABIT) as Habit

        findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

        val viewModel = ViewModelProvider(this)[CountDownViewModel::class.java]

        //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
        viewModel.setInitialTime(habit.minutesFocus)
        Log.e("habitMinuteFocus", "onCreate: ${habit.minutesFocus}")
        //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            viewModel.startTimer(habit.id, habit.title)
            updateButtonState(true)
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            viewModel.resetTimer()
            viewModel.eventCountDownFinish.observe(this) {
                updateButtonState(false)
            }


        }

        viewModel.currentTime.observe(this) { newTime ->
            newTime?.let {
                currentTime = it
                viewModel.currentTimeString.observe(this) { time ->
                    findViewById<TextView>(R.id.tv_count_down).text = time
                }
            }
        }

        viewModel.eventCountDownFinish.observe(this) {
            if (it) {
                updateButtonState(false)
            }
        }
    }


    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}