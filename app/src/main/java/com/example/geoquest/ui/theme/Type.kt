package com.example.geoquest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.geoquest.R

val robotoFamily = FontFamily(
    Font(R.font.roboto_serif, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(fontFamily = robotoFamily),
    displayMedium = TextStyle(fontFamily = robotoFamily),
    displaySmall = TextStyle(fontFamily = robotoFamily),
    headlineLarge = TextStyle(fontFamily = robotoFamily),
    headlineMedium = TextStyle(fontFamily = robotoFamily),
    headlineSmall = TextStyle(fontFamily = robotoFamily),
    titleLarge = TextStyle(fontFamily = robotoFamily),
    titleMedium = TextStyle(fontFamily = robotoFamily),
    titleSmall = TextStyle(fontFamily = robotoFamily),
    bodyLarge = TextStyle(fontFamily = robotoFamily),
    bodyMedium = TextStyle(fontFamily = robotoFamily),
    bodySmall = TextStyle(fontFamily = robotoFamily),
    labelLarge = TextStyle(fontFamily = robotoFamily),
    labelMedium = TextStyle(fontFamily = robotoFamily),
    labelSmall = TextStyle(fontFamily = robotoFamily)
)
