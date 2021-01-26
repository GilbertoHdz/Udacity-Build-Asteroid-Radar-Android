package com.gilbertohdz.asteroidradar.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gilbertohdz.asteroidradar.local.AsteroidDB
import com.gilbertohdz.asteroidradar.repository.AsteroidRepository
import java.lang.Exception

class AsteroidWorkManager(
    context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val asteroidRepo = AsteroidRepository(AsteroidDB.getInstance(applicationContext))

        return try {
            asteroidRepo.refreshPicture()
            asteroidRepo.refreshAsteroids()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object{
        const val WORK_NAME = "AsteroidWorkManager"
    }
}