package com.example.geoquest.ui.theme

import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary =  Color(0xFFE6F1F8), // white text
    secondary = Color(0xFFFDD391), // background of progress bar
    tertiary = Color(0xFFE6F1F8), // stroke for the item
    background = Color(0xFF010916),

    // ONLY USED FOR THE GRADIENT
    surface = Color(0xFF9F2E52),
    surfaceVariant = Color(0xFFD64B40)
)


private val LightColorScheme = lightColorScheme(
    primary =  Color(0xFF010916), // white text
    secondary = Color(0xFF010916), // background of progress bar
    tertiary = Color(0xFF010916).copy(0.4f), // stroke for the item
    background = Color.White,

    // ONLY USED FOR THE GRADIENT
    surface = Color(0xFF010916),
    surfaceVariant = Color(0xFF35578F)
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
