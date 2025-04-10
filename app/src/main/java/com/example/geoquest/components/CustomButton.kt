package com.example.geoquest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize

data class ButtonProps(
    val label: String,
    val onClick: () -> Unit,
)

@Composable
fun CustomButton(props: ButtonProps, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .clip(CircleShape)
                .background(getGradient()),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = props.onClick,
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = 180.dp,
                        height = 30.dp
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = props.label,
                    fontSize = getSize(TextType.ButtonText),
                    color = Color.White
                )
            }
        }
    }
}
