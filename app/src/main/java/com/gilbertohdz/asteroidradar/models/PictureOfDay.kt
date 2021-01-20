package com.gilbertohdz.asteroidradar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "picture_of_day_table")
data class PictureOfDay(
    @Json(name = "media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String
) {
    @PrimaryKey(autoGenerate = true)
    val pictureId: Long = 0L
}