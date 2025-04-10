package com.example.geoquest.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class ButtonProps(
    val label : String,
    val onClick : () -> Unit,
    val prefixIcon : ImageVector?
)

@Composable
fun CustomButton(props: ButtonProps,modifier: Modifier){
    // add check for the prefix icon (return  IconButton)
    // create the custom style for the buttons
    Button(
        modifier = modifier,
        onClick = props.onClick,

    ) { Text(props.label) }
}