package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.storyapp.api.retrofit.ApiConfig
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.data.pref.UserPreferences
import com.dicoding.storyapp.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository = runBlocking {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        UserRepository.getInstance(pref, apiService)
    }
}