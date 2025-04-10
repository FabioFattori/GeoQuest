package com.example.geoquest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize

data class GenericInputProps(
    val icon: ImageVector,
    val label: String,
    val text : MutableState<String>
)

@Composable
fun GenericInput(props: GenericInputProps){
    Box(
        modifier = Modifier.padding(bottom = 20.dp)
    ){
        TextField(
            value = props.text.value,
            onValueChange = {props.text.value = it},
            placeholder = {
                Text(
                    text = props.label,
                    fontSize = getSize(TextType.Normal)
                ) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    height = 70.dp
                )
                .clip(
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    brush = getGradient(),
                    shape = CircleShape
                ),
            textStyle = TextStyle(
                fontSize = getSize(TextType.Normal),
                color = MaterialTheme.colorScheme.primary
            ),
            leadingIcon = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                        .size(
                            width = 45.dp,
                            height = 45.dp
                        )
                        .background(
                        brush = getGradient(),
                        shape = CircleShape
                    )
                ){
                    Icon(
                        imageVector = props.icon,
                        contentDescription = props.label,
                        modifier = Modifier
                            .size(
                                width = 35.dp,
                                height = 35.dp
                            )
                    )
                }

            }
        )
    }
}