package com.example.imagepicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagepicker.adapter.BucketAdapter
import com.example.imagepicker.databinding.ActivityBucketBinding
import com.example.imagepicker.viewModel.BucketViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BucketListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBucketBinding
    private val viewModel: BucketViewModel by viewModel()
    private val adapter = BucketAdapter()

    companion object {
        fun startForResult(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, BucketListActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bucket)
        binding.lifecycleOwner = this

        setUpViews()
        setUpViewModels()
    }

    private fun setUpViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.itemClickedListener {
            setResult(RESULT_OK, Intent().apply { putExtra("bucketName", it.id.toString()) })
            finish()
        }

    }

    private fun setUpViewModels() {
        with(viewModel) {
            bucketList.observe(this@BucketListActivity, Observer { adapter.replaceAll(it) })
            getBuckets()
        }
    }
}