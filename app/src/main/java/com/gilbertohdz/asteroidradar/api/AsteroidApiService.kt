package com.gilbertohdz.asteroidradar.api

import com.gilbertohdz.asteroidradar.Constants.BASE_URL
import com.gilbertohdz.asteroidradar.Constants.NASA_API_KEY
import com.gilbertohdz.asteroidradar.models.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AsteroidApiService {

    @GET("neo/rest/v1/feed?api_key=${NASA_API_KEY}")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<ResponseBody>

    @GET("planetary/apod?api_key=${NASA_API_KEY}")
    suspend fun getPlanetaryApod(): Response<PictureOfDay>
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }
}