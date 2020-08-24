package com.example.imagepicker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imagepicker.R
import com.example.imagepicker.databinding.LayoutBucketItemBinding
import com.example.imagepicker.databinding.LayoutImageItemBinding
import com.example.imagepicker.models.Bucket
import com.example.imagepicker.models.Image
import kotlinx.android.synthetic.main.layout_image_item.view.*

class ImageAdapter : PagedListAdapter<Image, ImageAdapter.ImageViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.uri == newItem.uri
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.bind<LayoutImageItemBinding>(
            layoutInflater.inflate(
                R.layout.layout_image_item,
                parent,
                false
            )
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ImageViewHolder(private val binding: LayoutImageItemBinding?) :
        RecyclerView.ViewHolder(binding!!.root) {

        fun bind(image: Image) {
            binding?.apply {
                this.image = image
            }
        }
    }
}