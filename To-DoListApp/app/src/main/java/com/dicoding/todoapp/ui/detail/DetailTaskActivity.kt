package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailTaskViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action

        val title = findViewById<TextInputEditText>(R.id.detail_ed_title)
        val desc = findViewById<TextInputEditText>(R.id.detail_ed_description)
        val dueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)
        val delete = findViewById<Button>(R.id.btn_delete_task)
        val tasks = intent.getIntExtra(TASK_ID, -1)

        viewModel.task.observe(this) {
            if (it != null) {
                title.setText(it.title)
                desc.setText(it.description)
                dueDate.setText(DateConverter.convertMillisToString(it.dueDate))
            }

        }

        viewModel.setTaskId(tasks)

        delete.setOnClickListener {
            viewModel.deleteTask()
            finish()
        }


    }
}