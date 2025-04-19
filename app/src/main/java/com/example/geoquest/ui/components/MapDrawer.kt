package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.classes.Position
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location


@Composable
fun MapDrawer(modifier: Modifier, playerPosition: Position, poiList: List<DayPointOfInterest>) {
    val viewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(16.0)
            center(Point.fromLngLat(playerPosition.lon, playerPosition.lat))
            pitch(0.0)
            bearing(0.0)
        }
    }

    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = viewportState,
    ) {
        MapEffect(Unit) { mapView ->
            mapView.location.updateSettings {
                locationPuck = createDefault2DPuck(withBearing = true)
                enabled = true
                puckBearing = PuckBearing.COURSE
                puckBearingEnabled = true
                pulsingEnabled = true
            }
            viewportState.transitionToFollowPuckState()
        }

    }
}

//
//@Composable
//fun PoiMarkers(pois: List<DayPointOfInterest>) {
//    pois.forEach { poi ->
//        MapAnnotation(
//            point = Point.fromLngLat(poi.lon, poi.lat),
//            content = {
//                val iconRes = when (poi.category) {
//                    "museum" -> R.drawable.ic_museum
//                    "monument" -> R.drawable.ic_monument
//                    "artwork" -> R.drawable.ic_art
//                    "park" -> R.drawable.ic_park
//                    else -> R.drawable.ic_default_poi
//                }
//
//                Icon(
//                    painter = painterResource(id = iconRes),
//                    contentDescription = poi.name,
//                    tint = Color.Unspecified,
//                    modifier = Modifier.size(32.dp)
//                )
//            }
//        )
//    }
//}
//
//@Composable
//fun PlayerMarker(position: Position) {
//    MapAnnotation(
//        point = Point.fromLngLat(position.lon, position.lat),
//        content = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_player), // metti la tua icona
//                contentDescription = "Player",
//                tint = Color.Unspecified,
//                modifier = Modifier.size(48.dp)
//            )
//        }
//    )
//}
