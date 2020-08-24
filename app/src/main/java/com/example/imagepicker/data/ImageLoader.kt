package com.example.imagepicker.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images.ImageColumns
import android.util.Log
import com.example.imagepicker.models.Bucket
import com.example.imagepicker.models.Image


class ImageLoader(private val context: Context) {
    private val projection = arrayOf(
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.DATE_ADDED,
        MediaStore.Images.ImageColumns.SIZE,
        MediaStore.Images.ImageColumns.MIME_TYPE,
        MediaStore.Images.ImageColumns.ORIENTATION
    )

    private val selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
            MediaStore.Images.Media.MIME_TYPE + "=? or " +
            MediaStore.Images.Media.MIME_TYPE + "=? or " +
            MediaStore.Images.Media.MIME_TYPE + "=?"

    private var selectionArgs = arrayOf("image/jpeg", "image/png", "image/gif", "image/heif")

    private val sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc"

    fun getBucketDisplays(): ArrayList<Bucket> {
        val list = arrayListOf<Bucket>()

        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            sortOrder
        )

        cursor?.let {
            while (it.moveToNext()) {
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
                val bucketColumn =
                    cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                val id = cursor.getLong(fieldIndex)
                val bucket = cursor.getString(bucketColumn)
                val bucketId = cursor.getLong(bucketIdColumn)
                val imageUri: Uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val bucketItem = Bucket(bucketId, bucket, imageUri)

                if (!list.contains(bucketItem)) {
                    list.add(bucketItem)
                    Log.d(
                        "getBucketDisplayList",
                        "id : $bucketId, bucketColumn : $bucket, imageUri : $imageUri"
                    )
                }

            }
            it.close()
        }
        return list
    }

    fun getImages(startPosition: Int, size: Int): ArrayList<Image> {
        val list = arrayListOf<Image>()
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            sortOrder
        )

        cursor?.let {
            it.moveToPosition(startPosition)

            val position = it.position
            val end = position + size

            while (it.moveToNext()) {
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri: Uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                list.add(Image(imageUri))
                Log.i("getMediaList", "id : $id, position : ${it.position}, end : $end ")

                if (it.position == end)
                    break
            }
            it.close()
        }
        return list
    }

    fun getBucketImages(bucketName: String, startPosition: Int, size: Int): ArrayList<Image> {
        val list = arrayListOf<Image>()
        val selection =
            "${MediaStore.Images.Media.BUCKET_ID} like ?"
        val selectionArgs = arrayOf(bucketName)

        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.let {
            it.moveToPosition(startPosition)

            val position = it.position
            val end = position + size

            while (it.moveToNext()) {
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri: Uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                list.add(Image(imageUri))
                Log.i(
                    "getMediaList",
                    "bucketName : $bucketName id : $id, position : ${it.position}, end : $end "
                )

                if (it.position == end)
                    break
            }
            it.close()
        }
        return list
    }

    fun getBucketImages(bucketName: String): java.util.ArrayList<Image> {
        val list = arrayListOf<Image>()
        val selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ? "
        val selectionArgs = arrayOf(bucketName)

        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.let {
            while (it.moveToNext()) {
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri: Uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                list.add(Image(imageUri))
                Log.i("getMediaList", "imageUri : $imageUri")

            }
            it.close()
        }
        return list
    }
}