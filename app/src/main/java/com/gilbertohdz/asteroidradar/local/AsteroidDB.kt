package com.gilbertohdz.asteroidradar.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gilbertohdz.asteroidradar.entities.AsteroidEntity
import com.gilbertohdz.asteroidradar.entities.PictureOfDayEntity

@Database(entities = [
    AsteroidEntity::class,
    PictureOfDayEntity::class
], version = 1, exportSchema = false)
abstract class AsteroidDB : RoomDatabase (){

    /**
     * Connects the database to the DAO.
     */
    abstract val asteroidDao: AsteroidDAO

    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: AsteroidDB? = null

        fun getInstance(context: Context): AsteroidDB {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AsteroidDB::class.java,
                            "asteroid_database")
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}