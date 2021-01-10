package com.gilbertohdz.asteroidradar.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertohdz.asteroidradar.api.AsteroidApi
import com.gilbertohdz.asteroidradar.api.getFirstAndEndDate
import com.gilbertohdz.asteroidradar.api.parseAsteroidsJsonResult
import com.gilbertohdz.asteroidradar.models.Asteroid
import com.gilbertohdz.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

enum class AsteroidApiStatus { LOADING, ERROR, SUCCESS }

class MainViewModel : ViewModel() {

    private val _apiStatus: MutableLiveData<AsteroidApiStatus> = MutableLiveData()
    val apiStatus: LiveData<AsteroidApiStatus> = _apiStatus

    private val _asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val asteroids: LiveData<List<Asteroid>> = _asteroids

    private val _imageOfTheDay: MutableLiveData<PictureOfDay> = MutableLiveData()
    val imageOfTheDay: LiveData<PictureOfDay> = _imageOfTheDay

    init {
        getAsteroids()
        loadPictureOfTheDay()
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            _apiStatus.value = AsteroidApiStatus.LOADING

            try {
                val result = AsteroidApi.retrofitService.getAsteroids(getFirstAndEndDate().first, getFirstAndEndDate().second)

                if (result.isSuccessful) {
                    val body = result.body()?.string()
                    val jsonObject = JSONObject(body)

                    _asteroids.value = parseAsteroidsJsonResult(jsonObject)
                    _apiStatus.value = AsteroidApiStatus.SUCCESS
                } else {
                    _apiStatus.value = AsteroidApiStatus.ERROR
                }

            } catch (e: Exception) {
                _apiStatus.value = AsteroidApiStatus.ERROR
            }
        }
    }

    private fun loadPictureOfTheDay() {
        viewModelScope.launch {
            try {
                val result = AsteroidApi.retrofitService.getPlanetaryApod()
                if (result.isSuccessful) {
                    _imageOfTheDay.value = result.body()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message?: "error")
            }
        }
    }
}