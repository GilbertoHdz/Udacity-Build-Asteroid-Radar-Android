package com.gilbertohdz.asteroidradar.models

import com.gilbertohdz.asteroidradar.entities.PictureOfDayEntity
import com.squareup.moshi.Json

data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDay.asDatabaseModel(): PictureOfDayEntity {
    return PictureOfDayEntity(0, mediaType, title, url)
}