package com.example.geoquest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.geoquest.R
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.utilities.navigation.Screens

data class Route(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String
)

private fun GetSelectedBoxStyle(): Modifier {
    return Modifier
        .size(
            size = 90.dp
        )
        .clip(CircleShape)
}

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
) {

    val battleIcon = ImageVector.vectorResource(id = R.drawable.swords_24px)
    var selectedRoute = remember { mutableStateOf(Screens.Home.route) }

    val routes = remember {
        listOf<Route>(
            Route(
                icon = Icons.Outlined.Backpack,
                route = Screens.Inventory.route,
                contentDescription = "Inventory"
            ),
            Route(
                icon = Icons.Outlined.Map,
                route = Screens.Home.route,
                contentDescription = "Home"
            ),
            Route(
                icon = battleIcon,
                route = Screens.Battles.route,
                contentDescription = "Inventory"
            ),
        )
    }

    val iconSize: Dp = 60.dp

    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(
                150.dp
            ),
        containerColor = MaterialTheme.colorScheme.background,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 15.dp
                    )
            ) {
                for (route in routes) {
                    IconButton(
                        onClick = {
                            selectedRoute.value = route.route
                            navigator.navigate(route.route) {
                                popUpTo(Screens.Home.route) { inclusive = true }
                            }
                        },
                        modifier = (if (route.route == selectedRoute.value) {
                            GetSelectedBoxStyle().background(
                                brush = getGradient()
                            )
                        } else {
                            Modifier.size(90.dp)
                        })
                    ) {
                        Icon(
                            imageVector = route.icon,
                            contentDescription = route.contentDescription,
                            modifier = Modifier
                                .size(iconSize),
                            tint = if (selectedRoute.value == route.route) {
                                MaterialTheme.colorScheme.background
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        )
                    }
                }
            }
        }
    )
}
