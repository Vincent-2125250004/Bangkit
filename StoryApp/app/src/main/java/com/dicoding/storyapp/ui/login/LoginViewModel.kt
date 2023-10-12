package com.dicoding.storyapp.ui.login

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.data.pref.UserModel

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) =
        repository.login(email, password)

    suspend fun saveSessions(user: UserModel) {
        return repository.saveSession(user)
    }
}