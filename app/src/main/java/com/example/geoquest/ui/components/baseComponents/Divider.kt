package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.getGradient

@Composable
fun Divider(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .border(
                2.dp,
                shape = RoundedCornerShape(20.dp),
                brush = getGradient()
            )
    )
}