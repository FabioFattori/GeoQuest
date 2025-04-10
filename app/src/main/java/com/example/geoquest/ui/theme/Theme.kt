package com.example.geoquest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

private val DarkColorScheme = darkColorScheme(
    primary = whiteText, // white text
    secondary = orangeYellow, // background of progress bar
    tertiary = bgForItemsDarkTheme, // stroke for the item
    background = bgDark,
    inversePrimary = inputBgDark, // input background

    // ONLY USED FOR THE GRADIENT
    surface = orangeForGradient,
    surfaceVariant = purpleForGradient
)


private val LightColorScheme = lightColorScheme(
    primary = blackText,
    secondary = blackBgProgress,
    tertiary = bgForItemsLightTheme,
    background = bgLight,
    inversePrimary = inputBgLight,

    // ONLY USED FOR THE GRADIENT
    surface = blackForGradientLight,
    surfaceVariant = blueForGradientLight
)

@Composable
fun GeoQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun getGradient(): Brush {
    val steps = arrayOf(
        0.0f to MaterialTheme.colorScheme.surface,
        1f to MaterialTheme.colorScheme.surfaceVariant
    )

    return Brush.verticalGradient(
        colorStops = steps
    )
}
