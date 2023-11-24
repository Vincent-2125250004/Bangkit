package com.dicoding.pompomwikie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.pompomwikie.data.PompomRepository
import com.dicoding.pompomwikie.ui.detail.DetailViewModel
import com.dicoding.pompomwikie.ui.favorite.FavoriteViewModel
import com.dicoding.pompomwikie.ui.home.HomeViewModel

class ViewModelFactory(private val repository: PompomRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}