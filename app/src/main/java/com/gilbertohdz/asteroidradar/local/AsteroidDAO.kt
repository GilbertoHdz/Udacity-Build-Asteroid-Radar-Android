package com.gilbertohdz.asteroidradar.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gilbertohdz.asteroidradar.models.Asteroid
import com.gilbertohdz.asteroidradar.models.PictureOfDay

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroids: List<Asteroid>)

    @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date")
    fun getAllAsteroids(): LiveData<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictureOfDay: PictureOfDay)
}