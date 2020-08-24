package com.example.imagepicker.models

import android.net.Uri

data class Bucket(
    val id: Long,
    val displayName: String?,
    val displayThumbnail: Uri
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bucket

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}