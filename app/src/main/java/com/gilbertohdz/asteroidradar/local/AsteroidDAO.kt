package com.gilbertohdz.asteroidradar.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gilbertohdz.asteroidradar.entities.AsteroidEntity
import com.gilbertohdz.asteroidradar.entities.PictureOfDayEntity

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: AsteroidEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM asteroid_table ORDER BY date(close_approach_date)")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :date")
    fun getTodayAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date BETWEEN :startDate AND :endDate ORDER BY date(close_approach_date) ASC")
    fun getWeeklyAsteroids(startDate: String, endDate: String) : LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictureOfDay: PictureOfDayEntity)

    @Query("SELECT * FROM picture_of_day_table ORDER BY pictureId  DESC LIMIT 1")
    fun getLastPictureOfDay(): LiveData<PictureOfDayEntity>
}