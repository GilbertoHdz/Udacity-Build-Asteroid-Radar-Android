package com.gilbertohdz.asteroidradar.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertohdz.asteroidradar.Constants.NASA_API_KEY
import com.gilbertohdz.asteroidradar.api.AsteroidApi
import com.gilbertohdz.asteroidradar.api.getFirstAndEndDate
import com.gilbertohdz.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

enum class AsteroidApiStatus { LOADING, ERROR, SUCCESS }

class MainViewModel : ViewModel() {

    init {
        getAsteroids()
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val result = AsteroidApi.retrofitService.getAsteroids(getFirstAndEndDate().first, getFirstAndEndDate().second, NASA_API_KEY)
                if (result.isSuccessful) {
                    val test = result.body()?.string()
                    val jsd = JSONObject(test)
                    val utils = parseAsteroidsJsonResult(jsd)
                }

            } catch (e: Exception) {
               Log.i("", "")
            }
        }
    }
}