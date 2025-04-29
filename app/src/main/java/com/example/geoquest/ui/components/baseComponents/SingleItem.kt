package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.background
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

@Composable
fun SingleItem(
    modifier: Modifier,
    rarity: Color,
    image: @Composable () -> Unit,
    clickable: Boolean,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .size(100.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        rarity,
                        MaterialTheme.colorScheme.surface
                    ),
                    center = Offset(50f, 50f),
                    radius = 120f
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable(enabled = clickable) {
                onClick()
            }
    ) {
        image()
    }
}
