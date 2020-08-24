package com.example.imagepicker.paging

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.example.imagepicker.data.ImageLoader
import com.example.imagepicker.models.Image

class ImageDataSourceFactory(val imageLoader: ImageLoader) : DataSource.Factory<Int, Image>() {

    override fun create(): DataSource<Int, Image> {
        return ImagePositionalDataSource()
    }

    inner class ImagePositionalDataSource : PositionalDataSource<Image>() {
        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Image>) {
            val pageSize = params.pageSize
            val loadSize = params.requestedLoadSize
            val startPosition = params.requestedStartPosition
            val imageList = imageLoader.getImages(startPosition, pageSize)
            Log.i(
                "loadInitial",
                "pageSize : $pageSize, loadSize : $loadSize, startPosition : $startPosition"
            )

            callback.onResult(imageList, 0)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Image>) {
            val loadSize = params.loadSize
            val startPosition = params.startPosition
            val imageList = imageLoader.getImages(startPosition, loadSize)

            Log.i(
                "loadRange",
                "loadSize : $loadSize, startPosition : $startPosition"
            )

            callback.onResult(imageList)
        }
    }
}