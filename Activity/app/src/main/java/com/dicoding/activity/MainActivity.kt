package com.dicoding.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etWidth: EditText
    private lateinit var etHeight: EditText
    private lateinit var etLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etWidth = findViewById(R.id.et_width)
        etHeight = findViewById(R.id.et_height)
        etLength = findViewById(R.id.et_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        btnCalculate.setOnClickListener(this)

        if (savedInstanceState != null){
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_calculate) {
            val inputLength = etLength.text.toString().trim()
            val inputWidth = etWidth.text.toString().trim()
            val inputHeight = etHeight.text.toString().trim()
            var isEmptyFields = false
            if (inputLength.isEmpty()) {
                isEmptyFields = true
                etLength.error = "Field ini tidak boleh kosong"
            } else if (inputWidth.isEmpty()) {
                isEmptyFields = true
                etWidth.error = "Field ini tidak boleh kosong"
            } else if (inputHeight.isEmpty()) {
                isEmptyFields = true
                etHeight.error = "Field ini tidak boleh kosong"
            } else if (!isEmptyFields) {
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                tvResult.text = volume.toString()
            }

        }
    }
    companion object {
        private const val STATE_RESULT ="state_result"
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(STATE_RESULT , tvResult.text.toString())
    }
}