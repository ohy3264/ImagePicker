package com.example.imagepicker.util.databinding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageViewBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("imgUrl")
        fun setImgUrl(imageView: ImageView, imgUrl: String?) {
            Glide.with(imageView.context)
                .load(imgUrl.orEmpty())
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("imgUrl")
        fun setImgUrl(imageView: ImageView, uri: Uri?) {
            Glide.with(imageView.context)
                .load(uri)
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("imgUrl", "width", "height")
        fun setImgUrl(imageView: ImageView, uri: Uri?, width: Int, height: Int) {
            Glide.with(imageView.context)
                .load(uri)
                .override(width, height)
                .into(imageView)
        }
    }
}