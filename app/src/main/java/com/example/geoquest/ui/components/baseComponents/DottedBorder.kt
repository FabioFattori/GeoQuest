package com.example.geoquest.ui.components.baseComponents

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dottedBorder(
    color: Color,
    strokeWidth: Dp = 1.dp,
    dotSpacing: Dp = 4.dp,
    radius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(2f, dotSpacing.toPx()), 0f
            )
        )
        val rect = Rect(0f, 0f, size.width, size.height)
        drawRoundRect(
            color = color,
            topLeft = rect.topLeft,
            size = rect.size,
            style = stroke,
            cornerRadius = CornerRadius(radius.toPx())
        )
    }
)

fun Modifier.styledDashedBorder(
    brush: Brush,
    strokeWidth: Dp = 3.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 6.dp,
    cornerRadius: Dp = 12.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        )
        drawRoundRect(
            brush = brush,
            size = size,
            style = stroke,
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )
    }
)

fun Modifier.dottedBorderBrush(
    brush: Brush,
    strokeWidth: Dp = 1.dp,
    dotSpacing: Dp = 4.dp,
    radius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(2f, dotSpacing.toPx()), 0f
            )
        )
        val rect = Rect(0f, 0f, size.width, size.height)
        drawRoundRect(
            brush = brush,
            topLeft = rect.topLeft,
            size = rect.size,
            style = stroke,
            cornerRadius = CornerRadius(radius.toPx())
        )
    }
)
