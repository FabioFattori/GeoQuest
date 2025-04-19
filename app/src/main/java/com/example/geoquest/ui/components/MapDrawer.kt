package com.example.geoquest.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.geoquest.R
import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.classes.Position
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
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
        // player
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

        // POIs
        for (poi in poiList) {

            val marker = rememberIconImage(
                key = R.drawable.award_star_24px,
                painter = painterResource(R.drawable.award_star_24px)
            )
            PointAnnotation(
                point = Point.fromLngLat(poi.position.lon, poi.position.lat),
            ) {
                iconImage = marker
                interactionsState.onClicked {
                    Log.d("MapDrawer", "Clicked on POI: ${poi.name} with category: ${poi.category}")
                    true
                }
            }
        }

    }
}
