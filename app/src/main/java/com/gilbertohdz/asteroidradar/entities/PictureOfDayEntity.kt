package com.gilbertohdz.asteroidradar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gilbertohdz.asteroidradar.models.PictureOfDay

@Entity(tableName = "picture_of_day_table")
data class PictureOfDayEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val pictureId: Long,
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String
)

fun PictureOfDayEntity.asDomainModel(): PictureOfDay {
    return PictureOfDay(mediaType, title, url)
}

fun List<PictureOfDayEntity>.asDomainModel(): List<PictureOfDay> {
    return map { PictureOfDay(it.mediaType, it.title, it.url) }
}