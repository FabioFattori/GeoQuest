package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.viewModels.SingleItemController

class SingleItemConfiguration {
    companion object {
        val size = 100.dp
    }
}

@Composable
fun Modifier.borderOrGradient(showBorder: Boolean): Modifier = this.then(
    if (showBorder) {
        Modifier.border(
            width = 5.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(16.dp)
        )
    } else {
        Modifier.border(
            width = 5.dp,
            brush = getGradient(),
            shape = RoundedCornerShape(16.dp)
        )
    }
)

@Composable
fun SingleItem(
    modifier: Modifier,
    rarity: Color?,
    image: @Composable () -> Unit,
    clickable: Boolean,
    controller: SingleItemController = remember { SingleItemController() },
    onClick: () -> Unit = {}
) {
    val isClicked = controller.isClicked
    val size = SingleItemConfiguration.size
    val sizeOffset = size.value + 30
    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = if (isClicked.value) getGradient() else Brush.radialGradient(
                    colors = listOf(
                        rarity ?: MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.tertiary
                    ),
                    center = Offset(sizeOffset,sizeOffset),
                    radius = sizeOffset+5
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .borderOrGradient(!isClicked.value)
            .clickable(enabled = clickable) {
                onClick()
                isClicked.value = !isClicked.value
            },
        contentAlignment = Alignment.Center
    ) {
        image()
    }
}
