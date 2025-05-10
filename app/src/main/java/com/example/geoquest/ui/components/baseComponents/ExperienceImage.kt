package com.example.geoquest.ui.components.baseComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OfflineBolt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ExperienceImage(modifier: Modifier){
    Icon(
        imageVector = Icons.Rounded.OfflineBolt,
        contentDescription = "",
        modifier = modifier,
        tint = Color.Yellow
    )
}