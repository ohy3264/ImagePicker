package com.example.imagepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagepicker.R
import com.example.imagepicker.databinding.LayoutBucketItemBinding
import com.example.imagepicker.models.Bucket

class BucketAdapter : RecyclerView.Adapter<BucketAdapter.BucketViewHolder>() {
    private var bucketList = ArrayList<Bucket>()
    var callback: ((Bucket) -> Unit)? = null

    fun replaceAll(list: List<Bucket>) {
        bucketList.clear()
        bucketList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.bind<LayoutBucketItemBinding>(
            layoutInflater.inflate(
                R.layout.layout_bucket_item,
                parent,
                false
            )
        )
        return BucketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BucketViewHolder, position: Int) {
        holder.bind(bucketList[position])
    }

    override fun getItemCount() = bucketList.size

    fun itemClickedListener(callback: ((Bucket) -> Unit)) {
        this.callback = callback
    }

    inner class BucketViewHolder(private val binding: LayoutBucketItemBinding?) :
        RecyclerView.ViewHolder(binding!!.root) {

        fun bind(bucket: Bucket) {
            binding?.apply {
                this.bucket = bucket
                this.handler = EventHandler()
            }
        }

        inner class EventHandler {
            fun itemClickedListener(bucket: Bucket) {
                callback?.invoke(bucket)
            }
        }
    }
}