package com.gilbertohdz.asteroidradar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gilbertohdz.asteroidradar.models.Asteroid

@Entity(tableName = "asteroid_table")
data class AsteroidEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val asteroidId: Long,
    @ColumnInfo(name = "_id")
    val id: Long,
    @ColumnInfo(name = "code_name")
    val codename: String,
    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double,
    @ColumnInfo(name = "estimate_diameter")
    val estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean
)

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
                it.id,
                it.codename,
                it.closeApproachDate,
                it.absoluteMagnitude,
                it.estimatedDiameter,
                it.relativeVelocity,
                it.distanceFromEarth,
                it.isPotentiallyHazardous
        )
    }
}