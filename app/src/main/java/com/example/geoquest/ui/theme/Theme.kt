package com.example.geoquest.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

    val view = LocalView.current
    // Salta l'operazione se stiamo visualizzando il componente tramite dei devtools (es. preview)
    if (!view.isInEditMode) {
        SideEffect { // Esegue il blocco al termine di ogni recomposition
            val window = (view.context as Activity).window
            // Cambio del colore della status bar per Android 15+
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
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
