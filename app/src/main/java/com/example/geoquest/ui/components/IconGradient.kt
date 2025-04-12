package com.example.geoquest.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.geoquest.ui.theme.getGradient

@Composable
fun IconGradient(
    modifier: Modifier,
    contentDescription: String,
    icon: ImageVector
) {
    val gradient = getGradient()

    Icon(
        icon,
        contentDescription,
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache() {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = gradient,
                        blendMode = BlendMode.SrcAtop
                    )
                }
            },
    )
}
