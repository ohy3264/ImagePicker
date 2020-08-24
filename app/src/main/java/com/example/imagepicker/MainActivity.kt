package com.example.imagepicker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagepicker.adapter.ImageAdapter
import com.example.imagepicker.databinding.ActivityMainBinding
import com.example.imagepicker.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val adapter: ImageAdapter = ImageAdapter()
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE
        )

        binding.testButton.setOnClickListener {
            BucketListActivity.startForResult(this, REQUEST_BUCKET_DISPLAY)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getImages().observe(this, Observer { items ->
                    items?.let { adapter.submitList(items) }
                })
            } else {
                Toast.makeText(this, "권한을 획득하지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_BUCKET_DISPLAY && resultCode == RESULT_OK) {
            val bucketName = data?.getStringExtra("bucketName")
            viewModel.getBucketImages(bucketName.orEmpty()).observe(this, Observer { items ->
                items?.let { adapter.submitList(items) }
            })
        }
    }
}

const val REQUEST_BUCKET_DISPLAY = 1000