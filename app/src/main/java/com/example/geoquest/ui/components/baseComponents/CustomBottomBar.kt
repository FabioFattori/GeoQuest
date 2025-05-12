package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.geoquest.R
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.utilities.navigation.Screens

data class Route(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String
)

private fun Modifier.getSelectedBoxStyle(): Modifier {
    val baseSize = 80.dp
    val paddingBottom = 20.dp
    return this
        .width(baseSize)
        .height(baseSize + paddingBottom)
        .padding(bottom = paddingBottom)
        .clip(CircleShape)
}

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
) {

    val battleIcon = ImageVector.vectorResource(id = R.drawable.swords_24px)

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
                route = Screens.League.route,
                contentDescription = "Inventory"
            ),
        )
    }
    val navBackStackEntry = navigator.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry.value?.destination?.route
    if (currentRoute == null) {
        currentRoute = Screens.Home.route
    }

    val iconSize: Dp = 60.dp

    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
            .height(
                135.dp
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
                            navigator.navigate(route.route) {
                                popUpTo(Screens.Home.route) { inclusive = true }
                            }
                        },
                        modifier = (if (route.route == currentRoute) {
                            Modifier
                                .getSelectedBoxStyle()
                                .background(
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
                            tint = if (currentRoute == route.route) {
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
