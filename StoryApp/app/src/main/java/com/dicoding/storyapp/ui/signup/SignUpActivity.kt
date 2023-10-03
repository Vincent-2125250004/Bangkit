package com.dicoding.storyapp.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.databinding.ActivitySignUpBinding
import com.dicoding.storyapp.ui.ViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()

    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            var name = binding.edRegisterName.text.toString().trim()
            var email = binding.edRegisterEmail.text.toString().trim()
            var password = binding.edRegisterPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.nameEditTextLayout.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.nameEditTextLayout.error = null
            }

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Email tidak boleh kosong"
                return@setOnClickListener
            } else if (!email.contains("@")) {
                binding.emailEditTextLayout.error = "Email harus mengandung @"
                return@setOnClickListener
            } else {
                binding.emailEditTextLayout.error = null
            }

            if (password.length < 8) {
                binding.passwordEditTextLayout.error = "Password tidak boleh kurang dari 8 Karakter"
                return@setOnClickListener
            } else {
                binding.passwordEditTextLayout.error = null
            }

            name = binding.edRegisterName.text.toString()
            email = binding.edRegisterEmail.text.toString()
            password = binding.edRegisterPassword.text.toString()
            viewModel.register(name, email, password).observe(this) { result ->
                showLoading(true)
                if (result != null) {
                    when (result) {
                        is Result.Success -> {
                            showLoading(false)
                            AlertDialog.Builder(it.context).apply {
                                setTitle("You're Set !")
                                setMessage("Akun berhasil dibuat, Silahkan login yaa!")
                                setPositiveButton("Continue") { _, _ ->
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        is Result.Error -> {
                            showLoading(false)
                            AlertDialog.Builder(it.context).apply {
                                setTitle("Something Wrong !")
                                setMessage(result.error)
                                setNegativeButton("Fill Again") { _, _ ->

                                }
                                create()
                                show()
                            }
                        }

                        is Result.Loading -> {
                            showLoading(true)
                        }

                    }
                }
            }

        }

        binding.edRegisterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.nameEditTextLayout.error = null
            }
        })

        binding.edRegisterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.emailEditTextLayout.error = null
            }
        })

        binding.edRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.passwordEditTextLayout.error = null
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(150)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(150)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(150)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(150)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(150)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameEditTextLayout,
                emailEditTextLayout,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()

    }
}