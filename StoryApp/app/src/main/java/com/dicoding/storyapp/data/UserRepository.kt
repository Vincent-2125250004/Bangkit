package com.dicoding.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.storyapp.api.response.AllStoriesResponse
import com.dicoding.storyapp.api.response.ListStoryItem
import com.dicoding.storyapp.api.response.LoginResponse
import com.dicoding.storyapp.api.response.RegisterResponse
import com.dicoding.storyapp.api.response.UploadResponse
import com.dicoding.storyapp.api.retrofit.ApiService
import com.dicoding.storyapp.data.pref.UserModel
import com.dicoding.storyapp.data.pref.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository constructor(
    //Not Private cuz used in Injection
    val userPreferences: UserPreferences,
    val apiService: ApiService
) {
    //dataStore's Methods
    suspend fun saveSession(user: UserModel) {
        userPreferences.saveSessions(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    fun register(username: String, email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val success = apiService.register(username, email, password)
            emit(Result.Success(success))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(Result.Error(errorMessage))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val success = apiService.login(email, password)
            emit(Result.Success(success))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(Result.Error(errorMessage))
        }
    }

    //Stories Methods
    fun getStories(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response: AllStoriesResponse = apiService.getAllStories()
            emit(Result.Success(response.listStory))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AllStoriesResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(Result.Error(errorMessage))
        }
    }

    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoriesPagingSource(apiService)
            }
        ).liveData
    }

    fun storyLocation(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesWithLocation()
            emit(Result.Success(response.listStory))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AllStoriesResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(Result.Error(errorMessage))
        }
    }


    fun uploadStory(imageFile: File, description: String): LiveData<Result<UploadResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
                )

                val response = apiService.uploadImage(multipartBody, requestBody)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(jsonInString, UploadResponse::class.java)
                emit(Result.Error(errorResponse.message.toString()))
            }
        }

    suspend fun refreshStories() {
        try {
            apiService.getAllStories()
        } catch (e: Exception) {
            throw e
        }
    }


}