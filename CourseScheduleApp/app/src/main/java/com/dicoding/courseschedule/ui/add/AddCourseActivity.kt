package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = resources.getString(R.string.add_course)

        val factory = AddViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                var edCourse = findViewById<EditText>(R.id.ed_course_name).text.toString()
                var edLecturer = findViewById<EditText>(R.id.ed_lecturer).text.toString()
                var edNote = findViewById<EditText>(R.id.ed_note).text.toString()
                var spDay = findViewById<Spinner>(R.id.spinner_day).selectedItem.toString()
                val daysOfWeek = resources.getStringArray(R.array.day)
                val selectedDayIndex = daysOfWeek.indexOf(spDay)
                val day = if (selectedDayIndex != -1) selectedDayIndex else 1
                var startTime = findViewById<TextView>(R.id.start_time).text.toString()
                var endTime = findViewById<TextView>(R.id.end_time).text.toString()

                viewModel.insertCourse(edCourse, day, startTime, endTime, edLecturer, edNote)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker(view: View) {
        val tag = when (view.id) {
            R.id.ib_start_time -> getString(R.string.start_time)
            R.id.ib_end_time -> getString(R.string.end_time)
            else -> throw IllegalArgumentException("Invalid view ID")
        }
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)

        when (tag) {
            getString(R.string.start_time) -> {
                findViewById<TextView>(R.id.start_time).text = formattedTime
            }

            getString(R.string.end_time) -> {
                findViewById<TextView>(R.id.end_time).text = formattedTime
            }
        }
    }

}