package com.example.imagepicker.di

import com.example.imagepicker.data.ImageLoader
import com.example.imagepicker.repository.ImageRepository
import com.example.imagepicker.viewModel.BaseViewModel
import com.example.imagepicker.viewModel.BucketViewModel
import com.example.imagepicker.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    val appModule = module {
        single { ImageLoader(androidContext()) }
        single { ImageRepository(get()) }
    }

    val viewModel = module {
        viewModel { BaseViewModel() }
        viewModel { BucketViewModel(get()) }
        viewModel { MainViewModel(get()) }
    }
}