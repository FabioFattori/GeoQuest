package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class SingleItemConfiguration{
    companion object{
        val size = 100.dp
    }
}

@Composable
fun SingleItem(
    modifier: Modifier,
    rarity: Color?,
    image: @Composable () -> Unit,
    clickable: Boolean,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .size(SingleItemConfiguration.size)
            .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            rarity ?: MaterialTheme.colorScheme.tertiary, // centro
                            MaterialTheme.colorScheme.tertiary // bordi
                        ),
                        center = Offset(0.5f, 0.5f), // percentuale rispetto al contenitore
                        radius = Float.POSITIVE_INFINITY // si adatta al contenitore
                    ),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(enabled = clickable) {
                onClick()
            }
    ) {
        image()
    }
}
