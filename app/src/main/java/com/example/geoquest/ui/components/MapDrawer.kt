package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.classes.Position
import com.example.geoquest.ui.viewModels.CreateRewardViewModel
import com.example.geoquest.ui.viewModels.GenerateType
import com.example.geoquest.utilities.ImagesResolver
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
    val openPoiDialog = remember { mutableStateOf(false) }
    val clickedPoi = remember { mutableStateOf<DayPointOfInterest?>(null) }
    val randomRewardViewModel = CreateRewardViewModel()
    val isLoading = remember { mutableStateOf(randomRewardViewModel.isCreating.value) }
    val loadedValue = remember { mutableStateOf(randomRewardViewModel.generatedUsableItem.value) }
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
            val resource = remember { getResource(poi.category) }
            val marker = rememberIconImage(
                key = resource,
                painter = painterResource(resource)
            )
            PointAnnotation(
                point = Point.fromLngLat(poi.position.lon, poi.position.lat),
            ) {
                iconImage = marker
                interactionsState.onClicked {
                    clickedPoi.value = poi
                    openPoiDialog.value = true
                    true
                }
            }
        }

    }

    when {

        loadedValue.value != null -> {
            val item = loadedValue.value!!
            val textToDisplay = item.name

            RewardDialog(
                onConfirmation = {
                    randomRewardViewModel.clearGeneratedUsableItem()
                    loadedValue.value = null
                },
                image = {
                    val images = ImagesResolver.associateDbImagesToPossibleImages()
                    images[item.imageIndex]?.let { image ->
                        ImagesResolver.GetImageComponent(image)
                    }
                },
                rarity = item.rarity,
                title = textToDisplay
            )
        }

        isLoading.value -> {
            AlertDialog(
                onDismissRequest = {},
                text = {
                    Row(
                        modifier = Modifier
                            .width(120.dp)
                            .height(100.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                confirmButton = {}
            )
        }

        openPoiDialog.value -> {
            POIDialog(
                onDismissRequest = { openPoiDialog.value = false },
                onConfirmation = {
                    randomRewardViewModel.createReward(
                        GenerateType.UsableItem,
                        onFinished = {
                            loadedValue.value = randomRewardViewModel.generatedUsableItem.value
                            openPoiDialog.value = false
                        }
                    )
                    isLoading.value = true

                },
                poi = clickedPoi.value!!,
                playerPosition = playerPosition,
            )
        }
    }
}

fun getResource(category: String?): Int {
    return when (category) {
        "museum" -> R.drawable.museom
        "artwork" -> R.drawable.artwork
        "monument" -> R.drawable.monument
        "park" -> R.drawable.park
        "fountain" -> R.drawable.foutain
        "memorial" -> R.drawable.memorial
//        "peak" -> R.drawable.peak
        else -> R.drawable.monument // Fallback icon if category is not recognized
    }
}
