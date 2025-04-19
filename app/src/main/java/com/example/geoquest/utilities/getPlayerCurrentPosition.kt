package com.example.geoquest.utilities

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.geoquest.business.classes.Position
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun getCurrentPlayerLocation(context: Context, onLocationReceived: (Position) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener { location ->
        location?.let {
            val pos = Position(lat = it.latitude, lon = it.longitude)
            onLocationReceived(pos)
        }
    }
}
