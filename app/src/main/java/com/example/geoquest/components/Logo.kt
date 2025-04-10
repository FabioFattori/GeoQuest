package com.example.geoquest.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.geoquest.R

@Composable
fun Logo(modifier: Modifier){
    val image = painterResource(R.drawable.geoquest)
    Image(
        painter = image,
        contentDescription = "Logo of the application",
        modifier = modifier
    )
}
