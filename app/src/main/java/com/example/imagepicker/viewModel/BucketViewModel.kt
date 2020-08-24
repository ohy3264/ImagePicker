package com.example.imagepicker.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagepicker.models.Bucket
import com.example.imagepicker.models.Image
import com.example.imagepicker.repository.ImageRepository

class BucketViewModel(private val imageRepository: ImageRepository) : BaseViewModel() {
    private val _bucketList: MutableLiveData<ArrayList<Bucket>> = MutableLiveData()

    val bucketList: LiveData<ArrayList<Bucket>> get() = _bucketList

    fun getBuckets() {
        _bucketList.value = imageRepository.getBucketDisplays()
    }
}