package com.example.geoquest.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.geoquest.R

@Composable
fun Logo(modifier: Modifier){
    val image = painterResource(R.drawable.geoquest)
    val size = 150.dp
    Box(
        modifier = Modifier.size(size, size + 20.dp)
    ){
        Image(
            painter = image,
            contentDescription = "Logo of the application",
            contentScale = ContentScale.Crop
        )
    }
}
