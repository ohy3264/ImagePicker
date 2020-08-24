package com.example.imagepicker.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.imagepicker.data.ImageLoader
import com.example.imagepicker.models.Bucket
import com.example.imagepicker.models.Image
import com.example.imagepicker.paging.BucketImageDataSourceFactory
import com.example.imagepicker.paging.ImageDataSourceFactory

class ImageRepository(private val imageLoader: ImageLoader) {

    private val pageSize = 10

    private val initialLoadKey = 0

    private val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize * 2)
        .setEnablePlaceholders(false)
        .build()

    fun getImages(): LiveData<PagedList<Image>> {
        val dataSourceFactory = ImageDataSourceFactory(imageLoader)
        return LivePagedListBuilder(dataSourceFactory, config)
            .setInitialLoadKey(initialLoadKey)
            .build()
    }

    fun getBucketImages(bucketName: String): LiveData<PagedList<Image>> {
        val dataSourceFactory = BucketImageDataSourceFactory(bucketName, imageLoader)
        return LivePagedListBuilder(dataSourceFactory, config)
            .setInitialLoadKey(initialLoadKey)
            .build()
    }

    fun getBucketDisplays(): ArrayList<Bucket> {
        return imageLoader.getBucketDisplays()
    }

    /*fun getBucketImages(bucketName: String): ArrayList<Image> {
        return imageLoader.getBucketImages(bucketName)
    }*/
}