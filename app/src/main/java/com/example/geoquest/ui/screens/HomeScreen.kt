package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.business.classes.Position
import com.example.geoquest.ui.components.MapDrawer
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.PoiVIewModel
import com.example.geoquest.utilities.getCurrentPlayerLocation
import com.mapbox.android.core.permissions.PermissionsManager

@Composable
fun HomeScreen(modifier: Modifier) {
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
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                try {
                    getCurrentPlayerLocation(context) { position ->
                        pointViewModel.fetchPoi(position.lat, position.lon)
                        playerPosition.value = position
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }

        val position = playerPosition.value

        if (!isLoading.value && position != null) {
            MapDrawer(
                modifier = modifier,
                playerPosition = position,
                poiList = lst.value
            )
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

    }

}
