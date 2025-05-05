package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import com.example.geoquest.R
import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.classes.Position
import com.example.geoquest.business.models.CollectedPoi
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.InventoryItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.Rarity
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.ui.components.dialogs.POIDialog
import com.example.geoquest.ui.components.dialogs.RewardDialog
import com.example.geoquest.ui.viewModels.CreateRewardViewModel
import com.example.geoquest.ui.viewModels.GenerateType
import com.example.geoquest.ui.viewModels.PoiVIewModel
import com.example.geoquest.utilities.ImagesResolver
import com.example.geoquest.utilities.PreferenceManager
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import kotlin.random.Random


@Composable
fun MapDrawer(
    modifier: Modifier,
    playerPosition: Position,
    poiList: List<DayPointOfInterest>,
    collectedPoiList: List<CollectedPoi>
) {
    val openPoiDialog = remember { mutableStateOf(false) }
    val clickedPoi = remember { mutableStateOf<DayPointOfInterest?>(null) }
    val randomRewardViewModel = CreateRewardViewModel()
    val isLoading = remember { mutableStateOf(randomRewardViewModel.isCreating.value) }
    val loadedValue =
        remember { mutableStateOf<InventoryItem?>(randomRewardViewModel.generatedUsableItem.value) }
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
            var textToDisplay = ""
            var imageIndex = ""
            var rarity: Rarity? = null
            if (item is EquippableItem) {
                textToDisplay = item.blueprint.name
                imageIndex = item.blueprint.imagePath
                rarity = item.rarity
            } else if (item is UsableItem) {
                textToDisplay = item.name
                imageIndex = item.imageIndex
                rarity = item.rarity
            }

            RewardDialog(
                onConfirmation = {
                    randomRewardViewModel.clearGeneratedUsableItem()
                    randomRewardViewModel.clearGeneratedEquippableItem()
                    loadedValue.value = null
                    isLoading.value = false
                },
                image = {
                    val images = ImagesResolver.associateDbImagesToPossibleImages()
                    images[imageIndex]?.let { image ->
                        ImagesResolver.GetImageComponent(image)
                    }
                },
                rarity = rarity!!,
                title = textToDisplay
            )
        }

        isLoading.value -> {
            AlertDialog(
                onDismissRequest = {},
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                    val random = Random.nextInt(1, 11)
                    var generateType: GenerateType
                    val onFinished: () -> Unit
                    if (random % 2 == 0) {
                        generateType = GenerateType.UsableItem
                        onFinished = {
                            loadedValue.value = randomRewardViewModel.generatedUsableItem.value
                            openPoiDialog.value = false
                        }
                    } else {
                        generateType = GenerateType.EquippableItem
                        onFinished = {
                            loadedValue.value = randomRewardViewModel.generatedEquippableItem.value
                            openPoiDialog.value = false
                        }
                    }
                    PoiVIewModel().onPoiCollect(
                        collectedPoi = clickedPoi.value!!,
                        playerId = PreferenceManager.getObject("player", Player::class.java)!!.id
                    )

                    randomRewardViewModel.createReward(
                        typeToGenerate = generateType,
                        onFinished = onFinished
                    )
                    isLoading.value = true

                },
                poi = clickedPoi.value!!,
                playerPosition = playerPosition,
                isAlreadyCollected = clickedPoi.value!!.isAlreadyCollected(collectedPoiList)
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
