package com.dicoding.pompomwikie.di

import com.dicoding.pompomwikie.data.PompomRepository

object Injection {

    fun provideRepository(): PompomRepository {
        return PompomRepository.getInstance()
    }
}