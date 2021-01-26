package com.gilbertohdz.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.gilbertohdz.asteroidradar.api.AsteroidApi
import com.gilbertohdz.asteroidradar.api.getFirstAndEndDate
import com.gilbertohdz.asteroidradar.api.parseAsteroidsJsonResult
import com.gilbertohdz.asteroidradar.entities.asDomainModel
import com.gilbertohdz.asteroidradar.local.AsteroidDB
import com.gilbertohdz.asteroidradar.models.Asteroid
import com.gilbertohdz.asteroidradar.models.PictureOfDay
import com.gilbertohdz.asteroidradar.models.asDatabaseEntity
import com.gilbertohdz.asteroidradar.models.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.IllegalStateException
import java.time.LocalDate

enum class FilterBy { WEEKLY, TODAY, LOCAL }

class AsteroidRepository(private val database: AsteroidDB) {

    private val _filterBy = MutableLiveData(FilterBy.WEEKLY)
    private val filterBy : LiveData<FilterBy> = _filterBy

    fun applyFilter(filterBy: FilterBy){
        _filterBy.value = filterBy
    }

    val pictureOfDay: LiveData<PictureOfDay> = Transformations.map(database.asteroidDao.getLastPictureOfDay()) {
        it?.asDomainModel()
    }

    val asteroids: LiveData<List<Asteroid>?> = Transformations.switchMap(filterBy) { filter ->
        when (filter) {
            FilterBy.WEEKLY -> {
                Transformations.map(database.asteroidDao.getWeeklyAsteroids(getFirstAndEndDate().first, getFirstAndEndDate().second)) {
                    it?.asDomainModel()
                }
            }
            FilterBy.TODAY -> {
                Transformations.map(database.asteroidDao.getTodayAsteroids(getFirstAndEndDate().first)) {
                    it?.asDomainModel()
                }
            }
            FilterBy.LOCAL -> {
                Transformations.map(database.asteroidDao.getAllAsteroids()) {
                    it?.asDomainModel()
                }
            }
            else -> throw IllegalArgumentException("")
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val result = AsteroidApi.retrofitService.getAsteroids(getFirstAndEndDate().first, getFirstAndEndDate().second)

            if (result.isSuccessful) {
                val body = result.body()?.string()
                val jsonObject = JSONObject(body)
                database.asteroidDao.insert(parseAsteroidsJsonResult(jsonObject).asDatabaseEntity())
            } else {
                throw IllegalStateException("no_connection")
            }
        }
    }

    suspend fun refreshPicture() {
        withContext(Dispatchers.IO) {
            val result = AsteroidApi.retrofitService.getPlanetaryApod()

            if (result.isSuccessful) {
                database.asteroidDao.insert(result.body()!!.asDatabaseModel())
            } else {
                throw IllegalStateException("no_connection")
            }
        }
    }
}
