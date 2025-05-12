package com.example.geoquest.business.classes

import android.os.Parcelable
import com.example.geoquest.business.models.CollectedPoi
import kotlinx.serialization.SerialName
import java.util.Date
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlinx.parcelize.Parcelize

data class DayPointOfInterest(
    val name: String?, val position: Position, val category: String?
) {
    fun getDisplayName(): String {
        return if (name == null || name.isEmpty()) category!! else name
    }

    fun haversineDistance(pos1: Position, pos2: Position): Double {
        val rBig = 6371000.0 // raggio della Terra in metri
        val dLat = Math.toRadians(pos2.lat - pos1.lat)
        val dLon = Math.toRadians(pos2.lon - pos1.lon)
        val a =
            sin(dLat / 2).pow(2.0) + cos(Math.toRadians(pos1.lat)) * cos(Math.toRadians(pos2.lat)) * sin(
                dLon / 2
            ).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return rBig * c
    }

    fun isNearPlayer(playerPosition: Position, toleranceInMeters: Int = 20): Boolean {
        val distance = haversineDistance(this.position, playerPosition)
        return distance <= toleranceInMeters
    }

    fun isAlreadyCollected(lst: List<CollectedPoi>, hoursThreshold: Int = 4): Boolean {
        if (lst.isEmpty()) return false
        return lst.any {
            it.position.lat == position.lat && it.position.lon == position.lon &&
                    (Date().time - it.timeStamps.updatedAt.time) < hoursThreshold * 60 * 60 * 1000

        }
    }
}

@Parcelize
data class Position(
    @SerialName("latitude") val lat: Double, @SerialName("longitude") val lon: Double
) : Parcelable
