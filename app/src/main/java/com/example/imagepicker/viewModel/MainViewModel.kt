package com.example.imagepicker.viewModel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.imagepicker.models.Image
import com.example.imagepicker.repository.ImageRepository

class MainViewModel(private val imageRepository: ImageRepository) : BaseViewModel() {

    fun getImages(): LiveData<PagedList<Image>> {
        return imageRepository.getImages()
    }

    fun getBucketImages(bucketName: String): LiveData<PagedList<Image>> {
        return imageRepository.getBucketImages(bucketName)
    }
}