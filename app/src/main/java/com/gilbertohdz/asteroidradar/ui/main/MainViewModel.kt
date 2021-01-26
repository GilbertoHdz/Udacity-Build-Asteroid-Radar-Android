package com.gilbertohdz.asteroidradar.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gilbertohdz.asteroidradar.api.AsteroidApi
import com.gilbertohdz.asteroidradar.local.AsteroidDAO
import com.gilbertohdz.asteroidradar.local.AsteroidDB
import com.gilbertohdz.asteroidradar.models.Asteroid
import com.gilbertohdz.asteroidradar.models.PictureOfDay
import com.gilbertohdz.asteroidradar.repository.AsteroidRepository
import com.gilbertohdz.asteroidradar.repository.FilterBy
import com.gilbertohdz.asteroidradar.ui.LiveEvent
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AsteroidApiStatus { LOADING, ERROR, SUCCESS }

class MainViewModel(application: Application) : ViewModel() {

    private val asteroidRepository = AsteroidRepository(AsteroidDB.getInstance(application.applicationContext))

    private val _apiStatus: MutableLiveData<AsteroidApiStatus> = MutableLiveData()
    val apiStatus: LiveData<AsteroidApiStatus> = _apiStatus

    private val _navigateToDetail: MutableLiveData<Asteroid> = MutableLiveData()
    val navigateToDetail: LiveData<Asteroid> = _navigateToDetail.toSingleEvent()

    val asteroids = asteroidRepository.asteroids
    val imageOfTheDay = asteroidRepository.pictureOfDay

    init {
        refreshAsteroidsFromRepository()
        refreshPictureFromRepository()
    }

    fun filterBy(filter: FilterBy) {
        asteroidRepository.applyFilter(filter)
    }

    fun navigateToDetail(item: Asteroid) {
        _navigateToDetail.value = item
    }

    private fun refreshAsteroidsFromRepository() {
        viewModelScope.launch {
            _apiStatus.value = AsteroidApiStatus.LOADING

            try {
                asteroidRepository.refreshAsteroids()
                _apiStatus.value = AsteroidApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message?: "error")
                _apiStatus.value = AsteroidApiStatus.ERROR
            }
        }
    }

    private fun refreshPictureFromRepository() {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshPicture()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message?: "error")
            }
        }
    }

    fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
        val result = LiveEvent<T>()
        result.addSource(this) {
            result.value = it
        }
        return result
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(app) as T
            }

            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}