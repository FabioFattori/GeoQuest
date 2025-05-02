package com.example.geoquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.business.classes.Position
import com.example.geoquest.ui.components.MapDrawer
import com.example.geoquest.ui.components.baseComponents.IconGradient
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.PoiVIewModel
import com.example.geoquest.utilities.getCurrentPlayerLocation
import com.mapbox.android.core.permissions.PermissionsManager
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(modifier: Modifier, goToQuests: () -> Unit) {
    val pointViewModel = PoiVIewModel()
    val context = LocalContext.current

    if (!PermissionsManager.areLocationPermissionsGranted(context)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                stringResource(R.string.NoLocationPermission),
                fontSize = getSize(TextType.Normal),
                fontWeight = FontWeight.Bold
            )
        }
        return
    } else {

        val playerPosition = remember { mutableStateOf<Position?>(null) }
        val isLoading = remember { pointViewModel.arePOIsLoading }
        val lst = remember { pointViewModel.poiList }
        LaunchedEffect(Unit) {
            var needToLoad = true
            while (true) {
                if (PermissionsManager.areLocationPermissionsGranted(context)) {
                    try {
                        getCurrentPlayerLocation(context) { position ->
                            pointViewModel.fetchPoi(
                                position.lat,
                                position.lon,
                                needToLoad = needToLoad
                            )
                            playerPosition.value = position
                        }
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                }
                needToLoad = false
                delay(5_000L)
            }
        }


        val position = playerPosition.value

        if (!isLoading.value && position != null) {


            // the box doesn't let the map visualize correctly
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                MapDrawer(
                    modifier = modifier,
                    playerPosition = position,
                    poiList = lst.value
                )
                FloatingActionButton(
                    onClick = goToQuests,
                    modifier = Modifier
                        .offset(x = 340.dp, y = 20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    IconGradient(
                        icon = ImageVector.vectorResource(id = R.drawable.menu_book),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }


        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

    }
}
